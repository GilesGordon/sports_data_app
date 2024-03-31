package com.cs4520.assignment4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment4.databases.ProductRepository
import kotlinx.coroutines.launch


class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun loadProducts(isNetworkAvailable: Boolean, pageNumber: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = if (isNetworkAvailable) {
                    repository.getProducts(pageNumber)
                } else {
                    repository.getProducts()
                }
                _products.value = result
                _isError.value = result.isEmpty()
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }
}