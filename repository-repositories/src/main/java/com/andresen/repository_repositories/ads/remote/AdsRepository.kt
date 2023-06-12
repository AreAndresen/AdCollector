package com.andresen.repository_repositories.ads.remote

import com.andresen.repository_repositories.helper.network.DataResult


interface AdsRepository {

    suspend fun getAdssDto(): DataResult<out List<AdDto>>

    suspend fun putFavorite(ad: AdDto, enableFavourite: Boolean? = false): DataResult<out Unit>

}