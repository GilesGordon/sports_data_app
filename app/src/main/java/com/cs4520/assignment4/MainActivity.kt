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
import com.cs4520.assignment4.databases.Api
import com.cs4520.assignment4.databases.AppDatabase
import com.cs4520.assignment4.databases.SportsRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productApi = Api.sportsApi
        val productDao = AppDatabase.getDatabase(applicationContext).SportsDao()
        val repository = SportsRepository(productApi, productDao)
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
            LoginScreen { username, password, pageNumber ->
                // Handle login logic and navigate to the product list screen
                navController.navigate("productList/$pageNumber")
            }
        }
        composable(
            route = "productList/{pageNumber}",
            arguments = listOf(navArgument("pageNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val pageNumber = backStackEntry.arguments?.getInt("pageNumber") ?: 1
            ProductListScreen("basketball", repository)
        }
    }
}