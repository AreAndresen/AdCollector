package com.andresen.library_repositories.ads.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Url

interface AdsApiService {

    /*@GET("discover.json")
    @Headers("Accept: application/json")
    suspend fun getAds(): List<AdDto>*/

    @GET("baldermork/6a1bcc8f429dcdb8f9196e917e5138bd/raw/discover.json")
    @Headers("Accept: application/json")
    suspend fun getAds(): AdsDto//List<AdDto> // @Url link: String


    @PUT
    suspend fun putFavorite(
        @Url adFavouriteLink: String,
        @Body favoritesRequest: PutFavoritesRequestDto = PutFavoritesRequestDto()
    ): PutFavouriteDto
}