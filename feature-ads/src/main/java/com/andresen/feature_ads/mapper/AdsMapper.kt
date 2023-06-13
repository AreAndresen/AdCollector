package com.andresen.feature_ads.mapper

import com.andresen.feature_ads.model.AdTypeUi
import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsContentUi
import com.andresen.feature_ads.model.AdsTopSearchBar
import com.andresen.feature_ads.model.AdsUi
import com.andresen.feature_ads.model.AdsUiModel
import com.andresen.feature_ads.model.ImageUi
import com.andresen.library_repositories.ads.local.AdEntity
import com.andresen.library_repositories.ads.remote.AdDto
import com.andresen.library_repositories.ads.remote.AdTypeDto
import com.andresen.library_repositories.ads.remote.AdsDto

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
        adsDto: AdsDto
    ): AdsUi {
        return AdsUi(
            adsTopSearchBar = AdsTopSearchBar(
                query = ""
            ),
            adsContent = AdsContentUi.AdsContent(
                ads = AdsUiModel(
                    fetchMore = null,
                    size = adsDto.size,
                    version = adsDto.version,
                    items = mapAds(adsDto.items).take(30)  // todo quickfix to not overload
                )
            )
        )
    }


    private fun mapAdDtoToAdUi(
        adDto: AdDto
    ): AdUiModel {
        val imageBaseUrl = "https://images.finncdn.no/dynamic/480x360c/"
        val imageUrl = StringBuilder()
        imageUrl.append(imageBaseUrl)

        return AdUiModel(
            id = adDto.id,
            adType = when (adDto.adTypeDto) {
                AdTypeDto.BAP -> AdTypeUi.BAP
                AdTypeDto.REALESTATE -> AdTypeUi.REALESTATE
                AdTypeDto.B2B -> AdTypeUi.B2B
                AdTypeDto.Unknown -> AdTypeUi.Unknown
            },
            image = ImageUi(
                imageUrl = imageUrl.append(adDto.image?.url).toString(),
                width = adDto.image?.width,
                height = adDto.image?.height,
            ),
            price = adDto.price?.value ?: adDto.price?.total,
            location = adDto.location,
            title = adDto.description
        )
    }

    private fun mapAds(
        ads: List<AdDto>
    ): List<AdUiModel> {
        return ads.map { dtoItem ->
            mapAdDtoToAdUi(dtoItem)
        }
    }

    private fun mapEntityAds(
        ads: List<AdEntity>
    ): List<AdUiModel> {
        return ads.map { dtoItem ->
            mapAdEntityAdUi(dtoItem)
        }
    }

    fun mapAdUiToAdEntity(
        adUi: AdUiModel
    ): AdEntity {
        return AdEntity(
            id = adUi.id,
            title = adUi.title,
            price = adUi.price,
            imageUrl = adUi.image?.imageUrl,
            isFavourite = true,
            location = adUi.location
        )
    }

    fun mapAdEntityAdUi(
        adEntity: AdEntity
    ): AdUiModel {
        return AdUiModel(
            id = adEntity.id,
            title = adEntity.title,
            price = adEntity.price,
            image = ImageUi(
                imageUrl = adEntity.imageUrl,
                width = null,
                height = null,
            ),
            adType = AdTypeUi.Unknown,
            location = adEntity.location,
            isFavourite = adEntity.isFavourite
        )
    }


    fun showAllAds(
        state: AdsUi,
        adsDto: AdsDto
    ): AdsUi {
        val adsContent = state.adsContent
        val topSearchBar = state.adsTopSearchBar
        return state.copy(
            adsTopSearchBar = topSearchBar.copy(
                query = "",
                showFavourites = !topSearchBar.showFavourites
            ),
            adsContent = if (adsContent is AdsContentUi.AdsContent) {
                adsContent.copy(
                    ads = adsContent.ads.copy(
                        items = mapAds(adsDto.items).take(30) // todo quickfix to not overload
                    )
                )
            } else adsContent
        )
    }

    fun showFavouriteLocalAdsContent(
        state: AdsUi,
        adEntity: List<AdEntity>
    ): AdsUi {
        val adsContent = state.adsContent
        val topSearchBar = state.adsTopSearchBar

        return state.copy(
            adsTopSearchBar = topSearchBar.copy(
                query = "",
                showFavourites = !topSearchBar.showFavourites
            ),
            adsContent = if (adsContent is AdsContentUi.AdsContent) {
                adsContent.copy(
                    ads = adsContent.ads.copy(
                        items = mapEntityAds(adEntity)
                    )
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
            adsTopSearchBar = state.adsTopSearchBar.copy(
                query = query
            ),
            adsContent = if (adsContent is AdsContentUi.AdsContent) {
                adsContent.copy(
                    ads = adsContent.ads.copy(
                        items = adsContent.ads.items.filter { ad ->
                            ad.title.contains(query)
                        }
                    )
                )
            } else adsContent
        )
    }

    // todo fix flickr
    fun applyFavourite(state: AdsUi, adUi: AdUiModel): AdsUi {
        val adsContent = state.adsContent
        return state.copy(
            adsContent = if (adsContent is AdsContentUi.AdsContent) {
                adsContent.copy(
                    ads = adsContent.ads.copy(
                        items = adsContent.ads.items.map { ad ->
                            if (ad == adUi) {
                                ad.copy(
                                    isFavourite = !ad.isFavourite
                                )
                            } else ad
                        }
                    )
                )
            } else adsContent
        )
    }

}