package com.cs4520.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cs4520.assignment4.databases.MLS.SoccerApi
import com.cs4520.assignment4.databases.MLS.SoccerDatabase
import com.cs4520.assignment4.databases.NBA.BasketballApi
import com.cs4520.assignment4.databases.NBA.AppDatabase
import com.cs4520.assignment4.databases.NFL.FootballApi
import com.cs4520.assignment4.databases.NFL.FootballDatabase
import com.cs4520.assignment4.databases.SportsRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val basketballApi = BasketballApi.sportsApi
        val soccerApi = SoccerApi.sportsApi
        val footballApi = FootballApi.sportsApi
        val basketballDao = AppDatabase.getDatabase(applicationContext).basketballDao()
        val soccerDao = SoccerDatabase.getDatabase(applicationContext).soccerDao()
        val footballDao = FootballDatabase.getDatabase(applicationContext).footballDao()
        val repository = SportsRepository(basketballApi, basketballDao,
            soccerApi, soccerDao,
            footballApi, footballDao)
        setContent {
            MainScreen(repository)
        }
    }
}

@Composable
fun MainScreen(repository: SportsRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen { username, password, sport ->
                navController.navigate("productList/$sport")
            }
        }
        composable(
            route = "productList/{sport}",
            arguments = listOf(navArgument("sport") { type = NavType.StringType })
        ) { backStackEntry ->
            val sport = backStackEntry.arguments?.getString("sport") ?: ""
            ProductListScreen(sport, repository)
        }
    }
}