package com.andresen.library_repositories.ads.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AdEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AdDatabase : RoomDatabase() {

    abstract fun adDao(): AdDao

    companion object {
        fun createDao(context: Context): AdDao {
            return Room.databaseBuilder(
                context,
                AdDatabase::class.java,
                "ad_database.db"
            ).build().adDao()
        }

    }
}