package com.cs4520.assignment4.databases.NBA

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

@TypeConverters(
    NBAStatusConverter::class, NBALeagueConverter::class, NBACountryConverter::class,
    NBATeamsConverter::class, NBAScoresConverter::class)
@Entity(tableName = "games")
data class BasketballGameEntity(
    @PrimaryKey val id: Int,
    val date: String,
    val time: String,
    val timestamp: Int,
    val timezone: String,
    val stage: String?,
    val week: String?,
    val status: NBAStatus,
    val league: NBALeague,
    val country: NBACountry,
    val teams: NBATeams,
    val scores: NBAScores,
)

@Dao
interface BasketballDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(products: List<BasketballGameEntity>)

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<BasketballGameEntity>
}

@TypeConverters(
    NBAStatusConverter::class, NBALeagueConverter::class, NBACountryConverter::class,
    NBATeamsConverter::class, NBAScoresConverter::class)
@Database(entities = [BasketballGameEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketballDao(): BasketballDao

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