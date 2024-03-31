package com.cs4520.assignment4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.cs4520.assignment4.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Login.setOnClickListener {
            val username = binding.Username.text.toString()
            val password = binding.Password.text.toString()
            val pageNumber = binding.pageNumber.text.toString().toIntOrNull() ?: 1

            if (username == "admin" && password == "admin") {
                val bundle = Bundle().apply {
                    putInt("page", pageNumber)
                }
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_productListFragment, bundle)
            } else {
                Toast.makeText(activity, "Incorrect credentials", Toast.LENGTH_SHORT).show()
            }

            binding.Username.text.clear()
            binding.Password.text.clear()
            binding.pageNumber.text.clear()
        }
    }
}