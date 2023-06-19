package com.andresen.feature_ads.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.feature_ads.mapper.AdsMapper
import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsContentUi
import com.andresen.feature_ads.model.AdsUi
import com.andresen.library_repositories.ads.local.AdEntity
import com.andresen.library_repositories.ads.local.AdsLocalRepository
import com.andresen.library_repositories.ads.remote.AdsRepository
import com.andresen.library_repositories.helper.network.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AdsViewModel(
    private val adsRepository: AdsRepository,
    private val localRepository: AdsLocalRepository,
) : ViewModel() {

    private val mutableLocalFavourites: MutableStateFlow<List<AdEntity>> =
        MutableStateFlow(emptyList())

    private val mutableAdsState = MutableStateFlow(AdsMapper.loading())
    val state: StateFlow<AdsUi> = mutableAdsState


    init {
        viewModelScope.launch {
            localRepository.getAds().collectLatest { ads ->
                mutableLocalFavourites.value = ads
            }
        }

        createAds()
    }

    private fun createAds() {
        viewModelScope.launchPeriodically(60_000)
    }


    private fun CoroutineScope.launchPeriodically(
        repeatMillis: Long
    ) = this.launch {
        while (isActive) {
            when (val adsResult = adsRepository.getAdsDto()) {
                is DataResult.Success -> {
                    val adsDto = adsResult.data

                    when (mutableAdsState.value.adsContent) {
                        is AdsContentUi.AdsContent -> {
                            mutableAdsState.update { state ->
                                // if at favourites page, keep state - else update remote ads page
                                if (state.adsTopSearchBar.showFavourites) {
                                    state
                                } else {
                                    onUpdateRemoteAds(state, false)
                                }
                            }
                        }

                        else -> {
                            mutableAdsState.value = AdsMapper.createAdsContent(
                                adsDto = adsDto,
                            )
                        }
                    }
                }

                is DataResult.Error.AppError -> {
                    mutableAdsState.value = AdsMapper.error()
                }

                is DataResult.Error.NoNetwork -> {
                    mutableAdsState.value = AdsMapper.error()
                }
            }

            delay(repeatMillis)
        }
    }


    fun onToggleShowFavourites() {
        viewModelScope.launch {
            mutableAdsState.update { state ->
                if (!state.adsTopSearchBar.showFavourites) {
                    AdsMapper.getAllLocalFavouriteAds(
                        state = state,
                        adEntity = mutableLocalFavourites.value,
                    )
                } else {
                    onUpdateRemoteAds(state, true)
                }
            }
        }

    }

    fun onSearchChange(search: String) {
        mutableAdsState.update { state ->
            if (search.isNotEmpty()) {
                AdsMapper.applySearchQueryResult(state, search)
            } else {
                state
            }
        }
    }

    private suspend fun onUpdateRemoteAds(state: AdsUi, toggleFavourites: Boolean): AdsUi {
        return when (val adsResult = adsRepository.getAdsDto()) {
            is DataResult.Success -> {
                val adsDto = adsResult.data

                AdsMapper.updateRemoteAds(
                    state = state,
                    adsDto = adsDto,
                    toggleFavourites = toggleFavourites
                )
            }

            is DataResult.Error.AppError -> {
                AdsMapper.error()
            }

            is DataResult.Error.NoNetwork -> {
                AdsMapper.error()
            }
        }
    }


    fun onClearSearch() {
        viewModelScope.launch {
            mutableAdsState.update { state ->
                onUpdateRemoteAds(state, false)
            }
        }
    }

    fun toggleFavouriteAd(
        adUi: AdUiModel
    ) {
        viewModelScope.launch {
            mutableAdsState.update { state ->
                val adEntity = AdsMapper.mapAdUiToAdEntity(
                    adUi
                )

                if (adEntity.isFavourite) {
                    localRepository.insertAdFavourite(adEntity)
                } else {
                    localRepository.deleteAdFavourite(adEntity)
                }

                AdsMapper.toggleFavouriteAd(
                    state = state,
                    adUi = adUi,
                )
            }
        }
    }
}