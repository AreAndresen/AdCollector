package com.andresen.adcollector.main.koinmodules.ads

import android.content.Context
import com.andresen.feature_ads.viewmodel.AdsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module



object AdsModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
           module {
                viewModel {
                    AdsViewModel(get())
                }
            }
        )
    }
}

