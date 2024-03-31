package com.cs4520.assignment4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.Navigation
import com.cs4520.assignment4.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@Composable
fun LoginScreen(onLogin: (username: String, password: String, pageNumber: Int) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var pageNumber by remember { mutableStateOf("") }
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
            value = pageNumber,
            onValueChange = { pageNumber = it },
            label = { Text("Page Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                val enteredPageNumber = pageNumber.toIntOrNull() ?: 1

                if (enteredUsername == "admin" && enteredPassword == "admin") {
                    onLogin(enteredUsername, enteredPassword, enteredPageNumber)
                } else {
                    Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT).show()
                }

                username = ""
                password = ""
                pageNumber = ""
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

//class LoginFragment : Fragment() {
//
//    private lateinit var binding: FragmentLoginBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentLoginBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.Login.setOnClickListener {
//            val username = binding.Username.text.toString()
//            val password = binding.Password.text.toString()
//            val pageNumber = binding.pageNumber.text.toString().toIntOrNull() ?: 1
//
//            if (username == "admin" && password == "admin") {
//                val bundle = Bundle().apply {
//                    putInt("page", pageNumber)
//                }
//                Navigation.findNavController(view)
//                    .navigate(R.id.action_loginFragment_to_productListFragment, bundle)
//            } else {
//                Toast.makeText(activity, "Incorrect credentials", Toast.LENGTH_SHORT).show()
//            }
//
//            binding.Username.text.clear()
//            binding.Password.text.clear()
//            binding.pageNumber.text.clear()
//        }
//    }
//}