package com.cs4520.assignment4.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs4520.assignment4.R

@Composable
fun HomeScreen(onLogin: (sport: String?) -> Unit) {

    Column {

        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(24.dp))
                .height(200.dp)
                .clickable{
                    onLogin("basketball")
                }
                .testTag("NBA")
        ) {
            Image(painterResource(id = R.drawable.nba), contentDescription = "content", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop )
            Text(
                text = "NBA",
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black
                            )
                        )
                    )
                    .padding(12.dp),
                color = Color.White,
                //style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Left
            )
        }

        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable{
                    onLogin("soccer")
                }
                .testTag("MLS")
        ) {
            Image(painterResource(id = R.drawable.mls), contentDescription = "content")
            Text(
                text = "MLS",
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black
                            )
                        )
                    )
                    .padding(12.dp),
                color = Color.White,
               // style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        }

        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable{
                    onLogin("football")
                }
                .testTag("NFL")
        ) {
            Image(painterResource(id = R.drawable.nfl), contentDescription = "content")
            Text(
                text = "NFL",
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black
                            )
                        )
                    )
                    .padding(12.dp),
                color = Color.White,
              //  style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Left
            )
        }

    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(onLogin = {})
}