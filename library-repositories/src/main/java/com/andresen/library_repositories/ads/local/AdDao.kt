package com.andresen.library_repositories.ads.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AdDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdFavourite(ad: AdEntity)

    @Delete
    suspend fun deleteAdFavourite(ad: AdEntity)

    @Query("SELECT * FROM ads")
    fun getAds(): Flow<List<AdEntity>>
}