package com.cs4520.assignment4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cs4520.assignment4.databases.Api
import com.cs4520.assignment4.databases.AppDatabase
import com.cs4520.assignment4.databases.ProductRepository
import com.cs4520.assignment4.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productApi = Api.productApi
        val productDao = AppDatabase.getDatabase(applicationContext).productDao()
        val repository = ProductRepository(productApi, productDao)
        setContent {
            MainScreen(repository)
        }
    }
}

@Composable
fun MainScreen(repository: ProductRepository) {
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
            ProductListScreen(pageNumber, repository)
        }
    }
}