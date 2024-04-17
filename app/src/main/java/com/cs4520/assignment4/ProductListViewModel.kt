package com.cs4520.assignment4

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import com.cs4520.assignment4.coroutineworker.WorkManagerProvider
import com.cs4520.assignment4.databases.MLS.SoccerFixture
import com.cs4520.assignment4.databases.NBA.BasketballGame
import com.cs4520.assignment4.databases.NFL.FootballGame
import com.cs4520.assignment4.databases.SportsRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ProductListViewModel(private val repository: SportsRepository) : ViewModel() {

    //states
    private val _basketballGames = mutableStateOf<List<BasketballGame>>(emptyList())
    val basketballGames: State<List<BasketballGame>> get() = _basketballGames

    private val _soccerFixtures = mutableStateOf<List<SoccerFixture>>(emptyList())
    val soccerFixtures: State<List<SoccerFixture>> get() = _soccerFixtures

    private val _footballGames = mutableStateOf<List<FootballGame>>(emptyList())
    val footballGames: State<List<FootballGame>> get() = _footballGames

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> get() = _isError

    fun loadProducts(isNetworkAvailable: Boolean, sport: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                var result1: List<BasketballGame> = emptyList()
                var result2: List<SoccerFixture> = emptyList()
                var result3: List<FootballGame> = emptyList()

                if (sport == "basketball") {
                    result1 = repository.getBasketballGames() // league 12
                }
                if (sport == "soccer") {
                    result2 = repository.getSoccerFixtures() // league 909
                }
                if (sport == "football") {
                    result3 = repository.getFootballGames() // league 1
                }

                _basketballGames.value = result1
                _soccerFixtures.value = result2
                _footballGames.value = result3
                _isError.value = (result1.isEmpty() && result2.isEmpty() && result3.isEmpty())
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
        Log.d("ProductListViewModel", "Scheduled product refresh")
    }
}