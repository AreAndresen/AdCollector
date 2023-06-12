package com.andresen.library_repositories.ads.remote

import com.andresen.library_repositories.helper.network.DataResult


interface AdsRepository {

    suspend fun getAdsDto(): DataResult<out List<AdDto>>

    suspend fun putFavorite(ad: AdDto, enableFavourite: Boolean? = false): DataResult<out Unit>

}