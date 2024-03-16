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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.Login.setOnClickListener {
            if (binding.Username.text.toString() == "admin"
                && binding.Password.text.toString() == "admin") {
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                Toast.makeText(activity, "Incorrect credentials", Toast.LENGTH_SHORT).show()
            }
            binding.Username.text.clear()
            binding.Password.text.clear()
        }
    }
}