package com.andresen.repository_repositories.ads.remote

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AdDto(
    val id: String,
    @SerialName(value = "ad-type")
    val adType: AdType,
    @SerialName(value = "image")
    val image: ImageDto?,
    val price: Int?,
    val location: String?
)

@Serializable
data class ImageDto(
    val url: String,
    val width: Int,
    val heigh: Int,

    /*

    val type:
    "height": 800,
"width": 1200,
"type": "GENERAL",
"scalable": true
     */
)

/*
enum class Type(val value: String) {
    @Json(name = "manual")
    GENERAL("manual"),

    @Json(name = "consumed")
    Consumed("consumed"),
    Unknown("")
}*/

enum class AdType(val value: String) {
    @Json(name = "BAP")
    BAP("BAP"),

    @Json(name = "REALESTATE")
    REALESTATE("consumed"),

    @Json(name = "B2B")
    B2B("B2B"),

    Unknown("")
}


data class PutFavouriteDto(
    @Json(name = "_links") val links: LinkDto,
)

data class PutFavoritesRequestDto(
    val isFavourite: Boolean? = null
)

data class LinkDto(val href: String)