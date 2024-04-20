package com.cs4520.assignment4

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cs4520.assignment4.screens.HomeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp1() {
        var selectedSport: String? = null
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(onLogin = { sport -> selectedSport = sport })
            }
        }
    }
    @Test
    fun testHomeScreen(){
        composeTestRule.onNodeWithTag("NBA").performClick()
        composeTestRule.onNodeWithTag("NFL").performClick()
        composeTestRule.onNodeWithTag("MLS").performClick()
    }




}