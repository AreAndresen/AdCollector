package com.andresen.library_repositories.ads.remote

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdsDto(
    val items: List<AdDto>,
    val fetchMore: List<AdDto>?,
    val size: Int?,
    val version: String?
)


@Serializable
data class AdDto(
    val description: String,
    val id: String,
    val url: String?,
    @SerialName(value = "ad-type")
    val adTypeDto: AdTypeDto,
    val location: String?= null,
    val type: String?= null,
    @SerialName(value = "price")
    val price: Price?= null,
    @SerialName(value = "image")
    val image: ImageDto? = null,
    @SerialName(value = "shippingOption")
    val shippingOption: ShippingOption? = null,
    val score: Double?= null,
    val version: String?= null,
    val favourite: Favourite? = null
)

@Serializable
data class Price(
    val value: Int? = 0,
    val total: Int? = 0
)

@Serializable
data class ShippingOption(
    val label: String?
)


@Serializable
data class Favourite(
    val itemId: String?,
    val itemType: String?
)

@Serializable
data class ImageDto(
    val url: String?,
    val width: Int?,
    val height: Int?,
    val type: String?,
    val scalable: Boolean?
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

enum class AdTypeDto(val value: String) {
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