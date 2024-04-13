package com.cs4520.assignment4

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Composable
fun LoginScreen(onLogin: (username: String, password: String, sport: String?) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var sport by remember { mutableStateOf("") }
    val context = LocalContext.current

    ConstraintLayout (
        modifier = Modifier.fillMaxWidth())
    {
        val (user, pass, page, button) = createRefs()
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
        )
        TextField(
            value = sport,
            onValueChange = { sport = it },
            label = { Text("sport") },
            modifier = Modifier
                .constrainAs(page) {
                    top.linkTo(pass.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Button(
            onClick = {
                val enteredUsername = username.trim()
                val enteredPassword = password.trim()
                val enteredSport = sport.trim()

                if (enteredUsername == "admin" && enteredPassword == "admin") {
                    onLogin(enteredUsername, enteredPassword, enteredSport)
                } else {
                    Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                }

                username = ""
                password = ""
                sport = ""
            },
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(page.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.value(200.dp)
                }
        ) {
            Text("Login")
        }
    }
}