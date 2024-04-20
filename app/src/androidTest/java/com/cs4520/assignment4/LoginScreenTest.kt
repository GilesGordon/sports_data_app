package com.cs4520.assignment4

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.assignment4.screens.LoginScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

//    @Before
//    fun setUp() {
//
//        composeTestRule.setContent {
//
//            MaterialTheme {
//                val navController = rememberNavController()
//                LoginScreen(navController = navController)
//                MainScreen(repository = SportsRepository.getRepository(context = LocalContext.current))
//            }
//        }
//    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                val navController = rememberNavController()
                LoginScreen(navController = navController)
            }
        }
    }


    @Test
    fun testLogin() {


        composeTestRule.onNodeWithTag("username_input").performTextInput("admin")
        composeTestRule.onNodeWithTag("password_input").performTextInput("admin")
        composeTestRule.onNodeWithTag("login_button").performClick()
    }
}