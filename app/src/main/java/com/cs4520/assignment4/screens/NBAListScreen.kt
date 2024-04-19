package com.cs4520.assignment4.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.cs4520.assignment4.databases.NBA.BasketballGame
import com.cs4520.assignment4.databases.SportsRepository

@Composable
fun NBAListScreen(sport: String, repository: SportsRepository, navController: NavController, viewModel: ProductListViewModel) {
//    val viewModel = ProductListViewModel(repository)

    val gameResultUnordered by remember { viewModel.basketballGames }
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
        viewModel.scheduleProductRefresh(context)
        viewModel.loadProducts(isNetworkAvailable, sport) //specify sport
    }

    Log.d("APIResult", gameResultUnordered.toString())

    val gameResult = gameResultUnordered.reversed()

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
                        Text("NBA Games")
                    }

                }
                items(gameResult) { item ->
                    Log.d("Product", item.toString())
                    NBAListItem(item.teams.home.name,
                        item.teams.away.name,
                        item.teams.home.logo,
                        item.teams.away.logo,
                        item.scores.home.total,
                        item.scores.away.total,
                        item.date,
                        item.id,
                        navController)
                }
            }
        }
    }
}

@Composable
fun NBAListItem(homeTeamName: String,
                    awayTeamName: String,
                    homeTeamLogo: String,
                    awayTeamLogo: String,
                    homeTeamScore: Int,
                    awayTeamScore: Int,
                    gameDate: String,
                    gameId: Int,
                    navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable { navController.navigate("nbaStats/${gameId}") },
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
                    painter = rememberImagePainter(homeTeamLogo),
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
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberImagePainter(awayTeamLogo),
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
fun NBAListItemPreview() {
    NBAListItem("Warriors",
        "Wizards",
        "https://media.api-sports.io/basketball/teams/141.png",
        "https://media.api-sports.io/basketball/teams/161.png",
        4,
        2,
        "2021-10-10",
        1,
        rememberNavController())
}

//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//fun loadImage(path: String) {
//    GlideImage(
//        model = "https://content.sportslogos.net/logos/6/220/full/8190_atlanta_hawks-primary-2021.png",
//        contentDescription = "Image",
//        modifier = Modifier.size(50.dp),
//        contentScale = ContentScale.Crop
//    )
//}

//@OptIn(ExperimentalGlideComposeApi::class)
//@Preview
//@Composable
//fun GlideTest() {
//    GlideImage(
//        model = "https://m.media-amazon.com/images/I/61bRfnGfJTL.__AC_SX300_SY300_QL70_FMwebp_.jpg",
//        contentDescription = "Away Team",
//        modifier = Modifier.size(50.dp).clip(CircleShape),
//        contentScale = ContentScale.Crop
//    )
//}


//    ConstraintLayout(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        val (textView, recyclerView, progressBar) = createRefs()
//        if (products.isNotEmpty()) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .constrainAs(recyclerView) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            ) {
//                items(products) { product ->
//                    ProductItem(product)
//                }
//            }
//        }
//        if (!isLoading && (isError || products.isEmpty())) {
//            Text(
//                text = stringResource(R.string.no_products_available),
//                style = TextStyle(
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 30.sp
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .constrainAs(textView) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//        }
//
//        if (isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .constrainAs(progressBar) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//            )
//        }
//    }
//}

//@Composable
//fun ProductItem(product: Product) {
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(
//                when (product.type) {
//                    "Equipment" -> Color(0xFFE06666)
//                    "Food" -> Color(0xFFFFD965)
//                    else -> Color(0xFFFFD965)
//                }
//            )
//    ) {
//        val (image, name, date, price) = createRefs()
//
//        Image(
//            painter = painterResource(
//                when (product.type) {
//                    "Equipment" -> R.drawable.equipment
//                    "Food" -> R.drawable.food
//                    else -> 0
//                }
//            ),
//            contentDescription = null,
//            modifier = Modifier
//                .size(48.dp)
//                .constrainAs(image) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(parent.bottom)
//                }
//                .padding(start = 10.dp)
//        )
//
//        Text(
//            text = product.name,
//            color = Color.Black,
//            modifier = Modifier
//                .constrainAs(name) {
//                    top.linkTo(parent.top, margin = 15.dp)
//                    start.linkTo(image.end, margin = 10.dp)
//                }
//        )
//
//        if (product.expiryDate != null) {
//            Text(
//                text = product.expiryDate,
//                color = Color.Black,
//                modifier = Modifier
//                    .constrainAs(date) {
//                        top.linkTo(name.bottom, margin = 10.dp)
//                        start.linkTo(image.end, margin = 10.dp)
//                    }
//            )
//        }
//
//        Text(
//            text = product.price.toString(),
//            color = Color.Black,
//            modifier = Modifier
//                .constrainAs(price) {
//                    when (product.type) {
//                        "Equipment" -> top.linkTo(name.bottom, margin = 10.dp)
//                        "Food" -> top.linkTo(date.bottom, margin = 10.dp)
//                    }
//                    start.linkTo(image.end, margin = 10.dp)
//                    bottom.linkTo(parent.bottom, margin = 15.dp)
//                }
//        )
//    }
//}