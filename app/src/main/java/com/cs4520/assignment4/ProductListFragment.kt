package com.cs4520.assignment4

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.databinding.FragmentProductListBinding
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        val view = binding.root
        recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadProducts()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadProducts() {
        val productApi = Api.productApi
        val productDao = AppDatabase.getDatabase(requireContext()).productDao()
        val repository = ProductRepository(productApi, productDao)
        lifecycleScope.launch {
            try {
                if (isNetworkAvailable()) {
                    val products = repository.getProducts(0)
                    if (products.isEmpty()) {
                        binding.textView.visibility = View.VISIBLE
                    } else {
                        productAdapter = ProductAdapter(products)
                        recyclerView.adapter = productAdapter
                    }
                } else {
                    val cachedProducts = repository.getProducts()
                    if (cachedProducts.isEmpty()) {
                        binding.textView.visibility = View.VISIBLE
                    } else {
                        productAdapter = ProductAdapter(cachedProducts)
                        recyclerView.adapter = productAdapter
                    }
                }
                binding.progressBar.visibility = View.GONE
            } catch (e: Exception) {
                binding.textView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        var result = false
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        System.out.println("MyApp: network is " + result)
        return result
    }
}