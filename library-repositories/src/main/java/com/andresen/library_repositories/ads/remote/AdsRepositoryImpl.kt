package com.andresen.library_repositories.ads.remote

import com.andresen.library_repositories.helper.AdCollectorDispatchers
import com.andresen.library_repositories.helper.network.RequestHelper
import com.andresen.library_repositories.helper.network.DataResult
import kotlinx.coroutines.Dispatchers
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

    override suspend fun putFavorite(ad: AdDto, enableFavourite: Boolean?): DataResult<out Unit> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {

                val baseUrl = "https://images.finncdn.no/dynamic/480x360c/"

                val favouriteLinkWithUser = baseUrl +""+ad.image?.url

                api.putFavorite(
                    adFavouriteLink = favouriteLinkWithUser,
                    //ad
                )

                withContext(Dispatchers.IO) {
                    adsGlobalEvent.adUpdate()
                }
            }
        }


}