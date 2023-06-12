package com.andresen.feature_ads.mapper

import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsContentUi
import com.andresen.feature_ads.model.AdsTopSearchBar
import com.andresen.feature_ads.model.AdsUi
import com.andresen.library_repositories.ads.remote.AdDto

object AdsMapper {

    fun loading(): AdsUi = AdsUi(
        adsTopSearchBar = AdsTopSearchBar(
            query = ""
        ),
        adsContent = AdsContentUi.Loading
    )

    fun error(): AdsUi = AdsUi(
        adsTopSearchBar = AdsTopSearchBar(
            query = ""
        ),
        adsContent = AdsContentUi.Error
    )


    fun createAdsContent(
        adsDto: List<AdDto>
    ): AdsUi {
        return AdsUi(
            adsTopSearchBar = AdsTopSearchBar(
                query = ""
            ),
            adsContent = AdsContentUi.AdsContent(
                ads = mapAds(adsDto)
            )
        )
    }

    private fun mapAdDtoToAdUi(
        adDto: AdDto
    ): AdUiModel {
        return AdUiModel(
            id = adDto.id,
            imgSrc = adDto.image?.url
        )
    }

    private fun mapAds(
        ads: List<AdDto>
    ): List<AdUiModel> {
        return ads.map { dtoItem ->
            mapAdDtoToAdUi(dtoItem)
        }
    }

    fun emptySearch(
        state: AdsUi,
        adsDto: List<AdDto>
    ): AdsUi {
        val adsContent = state.adsContent
        val topSearchBar = state.adsTopSearchBar
        return state.copy(
            adsTopSearchBar = topSearchBar.copy(
                query = ""
            ),
            adsContent = if (adsContent is AdsContentUi.AdsContent) {
                adsContent.copy(
                    ads = mapAds(adsDto)
                )
            } else adsContent
        )
    }

    fun applySearchQueryResult(
        state: AdsUi,
        query: String,
    ): AdsUi {
        val adsContent = state.adsContent
        return state.copy(
            adsContent = if (adsContent is AdsContentUi.AdsContent) {
                adsContent.copy(
                    ads = adsContent.ads.filter { unit ->
                        unit.id.contains(query)
                    }
                )
            } else adsContent
        )
    }

    fun applySearchQuery(
        state: AdsUi,
        query: String
    ): AdsUi {
        val unitTopSearchBar = state.adsTopSearchBar

        return state.copy(
            adsTopSearchBar = unitTopSearchBar.copy(
                query = query
            )
        )
    }

}