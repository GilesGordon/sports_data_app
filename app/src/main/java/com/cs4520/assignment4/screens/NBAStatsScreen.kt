package com.cs4520.assignment4.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.rememberImagePainter
import com.cs4520.assignment4.ProductListViewModel
import com.cs4520.assignment4.databases.NBA.BasketballGame
import com.cs4520.assignment4.databases.NBA.NBAScore
import com.cs4520.assignment4.databases.NBA.NBATeam
import com.cs4520.assignment4.databases.SportsRepository
import kotlinx.coroutines.coroutineScope

@Composable
fun NBAStatsScreen(gameId: Int, viewModel: ProductListViewModel) {

    val basketballGames by remember { viewModel.basketballGames }
    Log.d("NBAStatsScreen", basketballGames.toString())
    val game = basketballGames.find { it.id == gameId }

    Log.d("NBAStatsScreen", game.toString())

    if (game != null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${game.teams.home.name} vs ${game.teams.away.name}", style = MaterialTheme.typography.h6)
            Text(text = "Date: ${game.date}")
            Text(text = "Time: ${game.time}")
            Text(text = "Timezone: ${game.timezone}")
            Text(text = "Stage: ${game.stage ?: "N/A"}")
            Text(text = "Week: ${game.week ?: "N/A"}")
            Text(text = "Status: ${game.status.long}")
            Text(text = "League: ${game.league.name}")
            Text(text = "Country: ${game.country.name}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Teams", style = MaterialTheme.typography.h5)
            TeamCard(team = game.teams.home, score = game.scores.home)
            Spacer(modifier = Modifier.height(8.dp))
            TeamCard(team = game.teams.away, score = game.scores.away)
        }
    } else {
        Text(text = "Game not found", style = MaterialTheme.typography.h6)
    }
}

@Composable
fun TeamCard(team: NBATeam, score: NBAScore) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = team.name, style = MaterialTheme.typography.h6, maxLines = 2, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
            val image = rememberImagePainter(data = team.logo)
            Image(painter = image, contentDescription = "Team Logo", modifier = Modifier.size(80.dp).align(Alignment.CenterHorizontally))
            Text(text = "Q1: ${score.quarter_1}")
            Text(text = "Q2: ${score.quarter_2}")
            Text(text = "Q3: ${score.quarter_3}")
            Text(text = "Q4: ${score.quarter_4}")
            Text(text = "Overtime: ${score.over_time ?: "N/A"}")
            Text(text = "Total: ${score.total}")
        }
    }
}


