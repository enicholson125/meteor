package com.enicholson125.meteor.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enicholson125.meteor.utilities.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(entities = [TextSnippet::class, AnimalType::class, AdoptedAnimal::class, SeenText::Class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun textSnippetDAO(): TextSnippetDAO

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase.class.java, DATABASE_NAME)
                .createFromAsset("database/seeddatabase.db")
                .build()
        }
    }
}