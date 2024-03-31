package com.cs4520.assignment4

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import com.cs4520.assignment4.databases.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ProductListViewModel(private val repository: ProductRepository) : ViewModel() {

    //states
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> get() = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> get() = _isError

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

    fun scheduleProductRefresh(context : Context) {
        val workManager = WorkManagerProvider.getWorkManager(context)
        workManager.cancelAllWork()

        // Create a new periodic work request
        val refreshRequest = PeriodicWorkRequestBuilder<ProductRefreshWorker>(1, TimeUnit.HOURS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "productRefresh",
            ExistingPeriodicWorkPolicy.REPLACE,
            refreshRequest
        )
    }
}