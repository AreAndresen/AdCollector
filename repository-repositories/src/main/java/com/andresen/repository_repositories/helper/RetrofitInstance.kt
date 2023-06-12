package com.andresen.repository_repositories.helper

import com.andresen.repository_repositories.ads.remote.AdsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: AdsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/baldermork/6a1bcc8f429dcdb8f9196e917e5138bd/raw/discover.json")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdsApiService::class.java)
    }
}