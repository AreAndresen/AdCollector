package com.andresen.feature_ads.model


data class AdsUi(
    val adsTopSearchBar: AdsTopSearchBar,
    val adsContent: AdsContentUi
)

sealed interface AdsContentUi {
    object Loading : AdsContentUi
    object Error : AdsContentUi
    data class AdsContent(
        val ads: AdsUiModel
    ) : AdsContentUi
}

data class AdsUiModel(
    val items: List<AdUiModel>,
    val fetchMore: List<AdUiModel>?,
    val size: Int?,
    val version: String?
)


data class AdUiModel(
    val id: String,
    val adType: AdTypeUi,
    val image: ImageUi?,
    val title: String,
    val price: Int?,
    val location: String?,
    val isFavourite: Boolean = false
)


data class ImageUi(
    val imageUrl: String?,
    val width: Int?,
    val height: Int?
)

enum class AdTypeUi { BAP, REALESTATE, B2B, Unknown }

data class AdsTopSearchBar(
    val query: String,
    val showFavourites: Boolean = false
)
