package com.andresen.feature_ads.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.feature_ads.mapper.AdsMapper
import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsUi
import com.andresen.library_repositories.ads.local.AdEntity
import com.andresen.library_repositories.ads.local.AdsLocalRepository
import com.andresen.library_repositories.ads.remote.AdsRepository
import com.andresen.library_repositories.helper.network.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

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
        viewModelScope.launch {
            when (val adsResult = adsRepository.getAdsDto()) {
                is DataResult.Success -> {
                    val adsDto = adsResult.data

                    val adsUi = AdsMapper.createAdsContent(
                        adsDto = adsDto,
                    )

                    mutableAdsState.value = adsUi
                }

                is DataResult.Error.AppError -> {
                    mutableAdsState.value = AdsMapper.error()
                }

                is DataResult.Error.NoNetwork -> {
                    mutableAdsState.value = AdsMapper.error()
                }
            }
        }
    }

    fun onToggleShowFavourites() {
        viewModelScope.launch {
                mutableAdsState.update { state ->
                    if (!state.adsTopSearchBar.showFavourites) {
                        AdsMapper.showFavouriteLocalAdsContent(
                            state = state,
                            adEntity = mutableLocalFavourites.value,
                        )
                    } else {
                        onGetAllAds(state)
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

    private suspend fun onGetAllAds(state: AdsUi): AdsUi {
        return when (val adsResult = adsRepository.getAdsDto()) {
            is DataResult.Success -> {
                val adsDto = adsResult.data

                AdsMapper.showAllAds(
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


    fun onClearSearch() {
        viewModelScope.launch {
            mutableAdsState.update { state ->
                onGetAllAds(state)
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