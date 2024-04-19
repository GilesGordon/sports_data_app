package com.cs4520.assignment4

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
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
import com.cs4520.assignment4.screens.HomeScreen
import com.cs4520.assignment4.screens.LoginScreen
import com.cs4520.assignment4.screens.MLSListScreen
import com.cs4520.assignment4.screens.NBAListScreen
import com.cs4520.assignment4.screens.NBAStatsScreen
import com.cs4520.assignment4.screens.NFLListScreen
import com.cs4520.assignment4.screens.MLSStatsScreen
import com.cs4520.assignment4.screens.NFLStatsScreen


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(repository: SportsRepository) {
    val navController = rememberNavController()
    val viewModel = ProductListViewModel(repository)
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
            val focusManager = LocalFocusManager.current
            focusManager.clearFocus()
        }
        composable("home") {
            HomeScreen { sport ->
                navController.navigate("productList/$sport")
            }
        }
        composable(
            route = "productList/{sport}",
            arguments = listOf(navArgument("sport") { type = NavType.StringType })
        ) { backStackEntry ->
            when (val sport = backStackEntry.arguments?.getString("sport") ?: "") {
                "basketball" -> NBAListScreen(sport, repository, navController, viewModel)
                "soccer" -> MLSListScreen(sport, repository, navController, viewModel)
                "football" -> NFLListScreen(sport, repository, navController, viewModel)
            }
        }
        composable(
            route = "nbaStats/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("id") ?: 0
            NBAStatsScreen(gameId, viewModel)
        }
        composable(
            route = "mlsStats/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("id") ?: 0
            MLSStatsScreen(gameId, viewModel)
        }
        composable(
            route = "nflStats/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("id") ?: 0
            NFLStatsScreen(gameId, viewModel)
        }
    }
}