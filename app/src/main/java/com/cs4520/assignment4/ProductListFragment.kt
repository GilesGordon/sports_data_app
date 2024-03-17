package com.cs4520.assignment4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        println("MyApp: onCreate")
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
        println("MyApp: load products")
        lifecycleScope.launch {
            try {
                println("MyApp: try")
                if (isNetworkAvailable()) {
                    val products = repository.getProducts()
                    println("MyApp: products again:" + products)
                    if (products.isEmpty()) {
                        println("MyApp: empty")
                        showNoProductsMessage()
                    } else {
                        println("MyApp: before adapter set")
                        productAdapter = ProductAdapter(products)
                        recyclerView.adapter = productAdapter
                        println("MyApp: after adapter set")
//                        saveProductsToDatabase(products)
                    }
                } else {
                    val cachedProducts = repository.getProducts()
                    if (cachedProducts.isEmpty()) {
                        showNoProductsMessage()
                    } else {
                        productAdapter = ProductAdapter(cachedProducts)
                        recyclerView.adapter = productAdapter
                    }
                }
            } catch (e: Exception) {
                // Handle the exception, e.g., show an error message
            }
        }
    }

    private fun showNoProductsMessage() {
        Toast.makeText(activity, "No Products", Toast.LENGTH_SHORT).show()
    }

    //TODO
    private fun isNetworkAvailable(): Boolean {
        return true
    }

//    private fun saveProductsToDatabase(products: List<Product>) {
//        val productDao = AppDatabase.getDatabase(requireContext()).productDao()
//        lifecycleScope.launch {
//            productDao.insertProducts(products)
//        }
//    }
}