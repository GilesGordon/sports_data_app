package com.cs4520.assignment4.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.cs4520.assignment4.ProductListViewModel
import com.cs4520.assignment4.databases.SportsRepository
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MLSListScreen(sport: String, repository: SportsRepository, navController: NavController, viewModel: ProductListViewModel) {
//    val viewModel = ProductListViewModel(repository)

    val gameResultUnordered by remember { viewModel.soccerFixtures }
    val isLoading by remember { viewModel.isLoading }
    val isError by remember { viewModel.isError }

    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    val isNetworkAvailable =
        networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

    LaunchedEffect(true) {
        // trigger work manager
        viewModel.scheduleProductRefresh(context)
        viewModel.loadProducts(isNetworkAvailable, sport) //specify sport
    }

    Log.d("APIResult", gameResultUnordered.toString())

    val gameResult = gameResultUnordered
        .reversed()
        .filter { LocalDate.parse(it.fixture.date.take(10)) <= LocalDate.now() }

    Column{
        if (gameResult.isEmpty()) {
            Text("Loading...")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 25.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("MLS Games")
                    }

                }
                items(gameResult) { item ->
                    Log.d("Product", item.toString())
                    // set default values
                    var homeScore = 0
                    var awayScore = 0
                    var period = "Full-time"

                    if(item.score.penalty.home != null && item.score.penalty.away != null) {
                        homeScore = item.score.penalty.home
                        awayScore = item.score.penalty.away
                        period = "Penalty"
                    } else if(item.score.extratime.home != null && item.score.extratime.away != null) {
                        homeScore = item.score.extratime.home
                        awayScore = item.score.extratime.away
                        period = "Extra-time"
                    } else {
                        homeScore = item.score.fulltime.home ?: 0
                        awayScore = item.score.fulltime.away ?: 0
                    }

                    MLSListItem(item.teams.home.name,
                        item.teams.away.name,
                        item.teams.home.logo,
                        item.teams.away.logo,
                        homeScore,
                        awayScore,
                        period,
                        item.fixture.date,
                        item.fixture.id,
                        navController)
                }
            }
        }
    }
}

@Composable
fun MLSListItem(homeTeamName: String,
                    awayTeamName: String,
                    homeTeamLogo: String,
                    awayTeamLogo: String,
                    homeTeamScore: Int,
                    awayTeamScore: Int,
                    period: String,
                    gameDate: String,
                    gameId: Int,
                    navController: NavController) {
    Card(
        modifier = Modifier.padding(16.dp).fillMaxWidth().height(200.dp).clickable { navController.navigate("mlsStats/${gameId}") },
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(homeTeamLogo), //painterResource(id = R.drawable.food),
//                    model = homeTeamLogo,
                    contentDescription = "Home Team",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = homeTeamName,
                    style = MaterialTheme.typography.h6.copy(fontSize = 12.sp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$homeTeamScore - $awayTeamScore",
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = gameDate.take(10),
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp)
                )
                Text(
                    text = period,
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(awayTeamLogo), // painterResource(id = R.drawable.equipment),
//                    model = awayTeamLogo,
                    contentDescription = "Away Team",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = awayTeamName,
                    style = MaterialTheme.typography.h6.copy(fontSize = 12.sp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// For testing purposes
@Preview
@Composable
fun MLSListItemPreview() {
    MLSListItem("Miami",
        "Chicago",
        "https://media.api-sports.io/basketball/teams/141.png",
        "https://media.api-sports.io/basketball/teams/161.png",
        4,
        2,
        "fulltime",
        "2021-10-10",
        1,
        rememberNavController())
}