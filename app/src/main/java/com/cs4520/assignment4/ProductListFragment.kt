package com.cs4520.assignment4
//
//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.NetworkCapabilities
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.cs4520.assignment4.databases.Api
//import com.cs4520.assignment4.databases.AppDatabase
//import com.cs4520.assignment4.databases.ProductRepository
//import com.cs4520.assignment4.databinding.FragmentProductListBinding
//
//class ProductListFragment : Fragment() {
//    private lateinit var binding: FragmentProductListBinding
//    private lateinit var productAdapter: ProductAdapter
//    private lateinit var viewModel: ProductListViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = FragmentProductListBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val productApi = Api.productApi
//        val productDao = AppDatabase.getDatabase(requireContext()).productDao()
//        val repository = ProductRepository(productApi, productDao)
//
//        viewModel = ViewModelProvider(this, object : ViewModelProvider.NewInstanceFactory() {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return ProductListViewModel(repository) as T
//            }
//        }).get(ProductListViewModel::class.java)
//
//        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
//        productAdapter = ProductAdapter(emptyList())
//        binding.recycleView.adapter = productAdapter
//
//        viewModel.products.observe(viewLifecycleOwner) { products ->
//            productAdapter = ProductAdapter(products)
//            binding.recycleView.adapter = productAdapter
//        }
//
//        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        viewModel.isError.observe(viewLifecycleOwner) { isError ->
//            binding.textView.visibility = if (isError) View.VISIBLE else View.GONE
//        }
//
//        val pageNumber = arguments?.getInt("page") ?: 1
//        viewModel.loadProducts(isNetworkAvailable(), pageNumber)
//    }
//
//    private fun isNetworkAvailable(): Boolean {
//        var result = false
//        val connectivityManager =
//            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkCapabilities = connectivityManager.activeNetwork ?: return false
//        val actNw =
//            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
//        result = when {
//            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//        return result
//    }
//}
//
