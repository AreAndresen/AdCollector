package com.andresen.library_repositories.ads.remote

import com.andresen.library_repositories.helper.AdCollectorDispatchers
import com.andresen.library_repositories.helper.network.DataResult
import com.andresen.library_repositories.helper.network.RequestHelper
import kotlinx.coroutines.withContext

class AdsRepositoryImpl(
    private val api: AdsApiService,
    private val requestHelper: RequestHelper,
    private val dispatchers: AdCollectorDispatchers,
    private val adsGlobalEvent: AdsGlobalEvent
) : AdsRepository {

    override suspend fun getAdsDto(): DataResult<out AdsDto> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {

                api.getAds()
            }
        }

    override suspend fun updateFavouriteAd(ad: AdDto, isFavourite: Boolean): DataResult<out Unit> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {

                api.updateFavouriteAd(
                    id = ad.id,
                    isFavourite = isFavourite,
                    favoriteAdRequest = PutFavoriteAdRequestDto(isFavourite)
                )
                // if i want to listen to favourites and call get in viewmodel to refresh
                adsGlobalEvent.adUpdate()
            }
        }
}