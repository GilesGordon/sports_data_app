package com.cs4520.assignment4.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController


@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    ConstraintLayout (
        modifier = Modifier.fillMaxWidth())
    {
        val (user, pass, button) = createRefs()
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .constrainAs(user) {
                    top.linkTo(parent.top, margin = 130.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .testTag("username_input")
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .constrainAs(pass) {
                    top.linkTo(user.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .testTag("password_input")
        )
        Button(
            onClick = {
                val enteredUsername = username.trim()
                val enteredPassword = password.trim()

                if (enteredUsername == "admin" && enteredPassword == "admin") {
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                }

                username = ""
                password = ""
            },
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(pass.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.value(200.dp)
                }
                .testTag("login_button")
        ) {
            Text("Login")
        }
    }
}