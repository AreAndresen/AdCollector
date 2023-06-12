package com.andresen.repository_repositories.ads.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Url

interface AdsApiService {

    @GET()
    @Headers("Accept: application/json")
    suspend fun getAds(): List<AdDto>


    @PUT
    suspend fun putFavorite(
        @Url adFavouriteLink: String,
        @Body favoritesRequest: PutFavoritesRequestDto = PutFavoritesRequestDto()
    ): PutFavouriteDto
}