package com.cs4520.assignment4.databases.NFL

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

@TypeConverters(GameConverter::class, LeagueConverter::class, TeamsConverter::class,
    ScoresConverter::class)
@Entity(tableName = "games")
data class FootballGameEntity(
    @PrimaryKey val game: Game,
    val league: League,
    val teams: Teams,
    val scores: Scores,
)

@Dao
interface FootballDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(products: List<FootballGameEntity>)

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<FootballGameEntity>
}

@TypeConverters(GameConverter::class, LeagueConverter::class, TeamsConverter::class,
    ScoresConverter::class)
@Database(entities = [FootballGameEntity::class], version = 1)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun footballDao(): FootballDao

    companion object {
        @Volatile
        private var INSTANCE: FootballDatabase? = null

        fun getDatabase(context: Context): FootballDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FootballDatabase::class.java,
                    "football_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}