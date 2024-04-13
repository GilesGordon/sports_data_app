package com.cs4520.assignment4

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import com.cs4520.assignment4.databases.SportsRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ProductListViewModel(private val repository: SportsRepository) : ViewModel() {

    //states
    private val _products = mutableStateOf<List<Game>>(emptyList())
    val products: State<List<Game>> get() = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> get() = _isError

    fun loadProducts(isNetworkAvailable: Boolean, sport: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                var result : List<Game> = emptyList()
                if (isNetworkAvailable && sport == "basketball") {
                    result = repository.getGames(12)
                }
                _products.value = result
                _isError.value = result.isEmpty()
            } catch (e: Exception) {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }

//    fun scheduleProductRefresh(context : Context) {
//        val workManager = WorkManagerProvider.getWorkManager(context)
//        workManager.cancelAllWork()
//
//        // Create a new periodic work request
//        val refreshRequest = PeriodicWorkRequestBuilder<ProductRefreshWorker>(1, TimeUnit.HOURS)
//            .build()
//        workManager.enqueueUniquePeriodicWork(
//            "productRefresh",
//            ExistingPeriodicWorkPolicy.REPLACE,
//            refreshRequest
//        )
//    }
}