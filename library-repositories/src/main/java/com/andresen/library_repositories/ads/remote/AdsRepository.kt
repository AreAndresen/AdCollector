package com.andresen.library_repositories.ads.remote

import com.andresen.library_repositories.helper.network.DataResult


interface AdsRepository {

    suspend fun getAdsDto(): DataResult<out AdsDto>

    suspend fun updateFavouriteAd(ad: AdDto, isFavourite: Boolean): DataResult<out Unit>

}