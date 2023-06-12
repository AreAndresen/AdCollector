package com.andresen.feature_ads.model

import kotlinx.serialization.Serializable


data class AdsUi(
    val adsTopSearchBar: AdsTopSearchBar,
    val adsContent: AdsContentUi
)

sealed interface AdsContentUi {
    object Loading : AdsContentUi
    object Error : AdsContentUi
    data class AdsContent(
        val ads: List<AdUiModel>
    ) : AdsContentUi
}

@Serializable
data class AdUiModel(
    val id: String,
    val imgSrc: String?
)

data class AdsTopSearchBar(
    val query: String
)
