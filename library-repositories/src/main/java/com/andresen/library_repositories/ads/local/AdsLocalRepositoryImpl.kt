package com.andresen.library_repositories.ads.local

import kotlinx.coroutines.flow.Flow

class AdsLocalRepositoryImpl(
    private val dao: AdDao
) : AdsLocalRepository {

    override suspend fun insertAdFavourite(ad: AdEntity) {
        dao.insertAdFavourite(ad)
    }

    override suspend fun deleteAdFavourite(ad: AdEntity) {
        dao.deleteAdFavourite(ad)
    }

    override fun getAds(): Flow<List<AdEntity>> {
        return dao.getAds()
    }
}