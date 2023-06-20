package com.andresen.library_repositories.ads.remote


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdsApiService {

    @GET("baldermork/6a1bcc8f429dcdb8f9196e917e5138bd/raw/discover.json")
    @Headers("Accept: application/json")
    suspend fun getAds(): AdsDto


    // mock PUT request
    @PUT("baldermork/6a1bcc8f429dcdb8f9196e917e5138bd/raw/items/{id}/favourite/{isFavorite}")
    suspend fun updateFavouriteAd(
        @Path("id") id: String,
        @Path("isFavourite") isFavourite: Boolean,
        @Body favoriteAdRequest: PutFavoriteAdRequestDto
    ): Response<Unit>
}