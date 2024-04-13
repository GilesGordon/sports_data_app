package com.cs4520.assignment4.databases.MLS

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
    FixtureConverter::class,
    LeagueConverter::class,
    TeamsConverter::class,
    GoalsConverter::class,
    ScoreConverter::class
)
@Entity(tableName = "fixtures")
data class SoccerFixtureEntity(
    @PrimaryKey val fixture: Fixture,
    val league: SoccerLeague,
    val teams: SoccerTeams,
    val goals: SoccerGoals,
    val score: SoccerScore,
)

@Dao
interface SoccerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixtures(products: List<SoccerFixtureEntity>)

    @Query("SELECT * FROM fixtures")
    suspend fun getAllFixtures(): List<SoccerFixtureEntity>
}

@TypeConverters(
    FixtureConverter::class,
    LeagueConverter::class,
    TeamsConverter::class,
    GoalsConverter::class,
    ScoreConverter::class
)
@Database(entities = [SoccerFixtureEntity::class], version = 1)
abstract class SoccerDatabase : RoomDatabase() {
    abstract fun soccerDao(): SoccerDao

    companion object {
        @Volatile
        private var INSTANCE: SoccerDatabase? = null

        fun getDatabase(context: Context): SoccerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SoccerDatabase::class.java,
                    "soccer_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}