package com.andresen.library_repositories.ads.local

import kotlinx.coroutines.flow.Flow

interface AdsLocalRepository {

    suspend fun insertAdFavourite(ad: AdEntity)

    suspend fun deleteAdFavourite(ad: AdEntity)

    fun getAds(): Flow<List<AdEntity>>
}