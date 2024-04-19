package com.cs4520.assignment4.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.cs4520.assignment4.ProductListViewModel
import com.cs4520.assignment4.databases.MLS.SoccerScore
import com.cs4520.assignment4.databases.MLS.SoccerTeam
import com.cs4520.assignment4.databases.SportsRepository

@Composable
fun MLSStatsScreen(fixtureId: Int, viewModel: ProductListViewModel) {

    val soccerGames by remember { viewModel.soccerFixtures }
    Log.d("MLSStatsScreen", soccerGames.toString())
    val fixture = soccerGames.find { it.fixture.id == fixtureId }

    Log.d("SoccerFixtureScreen", fixture.toString())

    if (fixture != null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Fixture ID: ${fixture.fixture.id}", style = MaterialTheme.typography.h6)
            Text(text = "Date: ${fixture.fixture.date}")
            Text(text = "Timezone: ${fixture.fixture.timezone}")
            Text(text = "Referee: ${fixture.fixture.referee ?: "N/A"}")
            Text(text = "Status: ${fixture.fixture.status.long}")
            Text(text = "League: ${fixture.league.name}")
            Text(text = "Country: ${fixture.league.country}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Teams", style = MaterialTheme.typography.h5)
            HomeTeamCard(team = fixture.teams.home, score = fixture.score)
            Spacer(modifier = Modifier.height(8.dp))
            AwayTeamCard(team = fixture.teams.away, score = fixture.score)
        }
    } else {
        Text(text = "Fixture not found", style = MaterialTheme.typography.h6)
    }
}

@Composable
fun HomeTeamCard(team: SoccerTeam, score: SoccerScore) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = team.name, style = MaterialTheme.typography.h6, maxLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            val image = rememberImagePainter(data = team.logo)
            Image(painter = image, contentDescription = "Team Logo", modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally))
            Text(text = "Halftime: ${score.halftime.home ?: "N/A"}")
            Text(text = "Fulltime: ${score.fulltime.home ?: "N/A"}")
            Text(text = "Extratime: ${score.extratime.home ?: "N/A"}")
            Text(text = "Penalty: ${score.penalty.home ?: "N/A"}")
        }
    }
}

@Composable
fun AwayTeamCard(team: SoccerTeam, score: SoccerScore) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = team.name, style = MaterialTheme.typography.h6, maxLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            val image = rememberImagePainter(data = team.logo)
            Image(painter = image, contentDescription = "Team Logo", modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally))
            Text(text = "Halftime: ${score.halftime.away ?: "N/A"}")
            Text(text = "Fulltime: ${score.fulltime.away ?: "N/A"}")
            Text(text = "Extratime: ${score.extratime.away ?: "N/A"}")
            Text(text = "Penalty: ${score.penalty.away ?: "N/A"}")
        }
    }
}

