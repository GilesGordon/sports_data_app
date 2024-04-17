package com.cs4520.assignment4

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment4.databases.SportsRepository

class ProductRefreshWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
//            val productApi = BasketballApi.sportsApi
//            val productDao = AppDatabase.getDatabase(applicationContext).BasketballDao()
            val repository = SportsRepository.getRepository(applicationContext)

            // Fetch products from the API and insert into DB using the repository
            repository.getBasketballGames()
            repository.getSoccerFixtures()
            repository.getFootballGames()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}