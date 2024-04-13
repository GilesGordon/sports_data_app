package com.cs4520.assignment4.databases

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cs4520.assignment4.Country
import com.cs4520.assignment4.League
import com.cs4520.assignment4.Scores
import com.cs4520.assignment4.Status
import com.cs4520.assignment4.Teams

@TypeConverters(StatusConverter::class, LeagueConverter::class, CountryConverter::class,
    TeamsConverter::class, ScoresConverter::class)
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val time: String,
    val timestamp: Int,
    val timezone: String,
    val stage: String?,
    val week: String?,
    val status: Status,
    val league: League,
    val country: Country,
    val teams: Teams,
    val scores: Scores,
)

@Dao
interface SportsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(products: List<GameEntity>)

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<GameEntity>
}

@TypeConverters(StatusConverter::class, LeagueConverter::class, CountryConverter::class,
    TeamsConverter::class, ScoresConverter::class)
@Database(entities = [GameEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun SportsDao(): SportsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}