package com.andresen.feature_ads.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.feature_ads.mapper.AdsMapper
import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsUi
import com.andresen.library_repositories.ads.local.AdsLocalRepository
import com.andresen.library_repositories.ads.remote.AdsRepository
import com.andresen.library_repositories.helper.network.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdsViewModel(
    private val adsRepository: AdsRepository,
    private val localRepository: AdsLocalRepository,
) : ViewModel() {

    private val mutableUnitsState = MutableStateFlow(AdsMapper.loading())
    val state: StateFlow<AdsUi> = mutableUnitsState

    init {
        createAds()
    }

    private fun createAds() {
        viewModelScope.launch {
            when (val adsResult = adsRepository.getAdsDto()) {


                is DataResult.Success -> {
                    val adsDto = adsResult.data

                    mutableUnitsState.value = AdsMapper.createAdsContent(
                        adsDto = adsDto,
                    )
                }

                is DataResult.Error.AppError -> {
                    mutableUnitsState.value = AdsMapper.error()
                }

                is DataResult.Error.NoNetwork -> {
                    mutableUnitsState.value = AdsMapper.error()
                }
            }

        }
    }

    fun onGetLocalFavourites() {
        viewModelScope.launch {
            localRepository.getAds().collectLatest { ads ->
                mutableUnitsState.value = AdsMapper.createFavouriteLocalAdsContent(
                    adEntity = ads,
                )
            }

        }
    }

    fun onTextChange(search: String) {
        mutableUnitsState.update { state ->
            if (search.isNotEmpty()) {
                AdsMapper.applySearchQueryResult(state, search)
            } else {
                state
            }
        }
    }

    fun onClearSearch() {
        viewModelScope.launch {
            mutableUnitsState.update { state ->
                when (val adsResult = adsRepository.getAdsDto()) {
                    is DataResult.Success -> {
                        val adsDto = adsResult.data

                        AdsMapper.emptySearch(
                            state = state,
                            adsDto = adsDto,
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
        }
    }

    fun favouriteAd(
        adUi: AdUiModel
    ) {
        viewModelScope.launch {
            mutableUnitsState.update { state ->
                val adEntity = AdsMapper.mapAdUiToAdEntity(
                    adUi
                )
                localRepository.insertAdFavourite(adEntity)

                AdsMapper.applyFavourite(
                    state = state,
                    adUi = adUi,
                )
            }
        }
    }
}