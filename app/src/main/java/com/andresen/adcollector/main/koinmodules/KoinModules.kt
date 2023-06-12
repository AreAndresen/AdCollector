package com.andresen.adcollector.main.koinmodules

import android.content.Context
import com.andresen.adcollector.MainViewModel
import com.andresen.adcollector.main.koinmodules.ads.AdsModule
import com.andresen.adcollector.main.koinmodules.repository.RepositoryModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {

    fun module(context: Context): List<Module> = listOf(
        createCommonModules(),
    )
        .union(RepositoryModule.createModules(context))
        .union(AdsModule.createModules(context))
        .toList()

    private fun createCommonModules(): Module {
        return module {

            viewModel {
                MainViewModel()
            }

        }
    }
}