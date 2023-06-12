package com.andresen.adcollector.main.koinmodules.repository

import android.content.Context
import com.andresen.library_repositories.ads.local.AdDatabase
import com.andresen.library_repositories.ads.local.AdsLocalRepository
import com.andresen.library_repositories.ads.local.AdsLocalRepositoryImpl
import com.andresen.library_repositories.ads.remote.AdsGlobalEvent
import com.andresen.library_repositories.ads.remote.AdsRepository
import com.andresen.library_repositories.ads.remote.AdsRepositoryImpl
import com.andresen.library_repositories.helper.AdCollectorDispatchers
import com.andresen.library_repositories.helper.AdCollectorDispatchersRegular
import com.andresen.library_repositories.helper.network.ApiServiceFactoryImpl
import com.andresen.library_repositories.helper.network.ConnectionService
import com.andresen.library_repositories.helper.network.ConnectionServiceImpl
import com.andresen.library_repositories.helper.network.RequestHelper
import org.koin.core.module.Module
import org.koin.dsl.module


object RepositoryModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                single { AdDatabase.createDao(get()) }
                single<AdsLocalRepository> {
                    AdsLocalRepositoryImpl(get())
                }
                factory { RequestHelper(get()) }
                single { ApiServiceFactoryImpl(get()) }
                factory<AdsRepository> {
                    AdsRepositoryImpl(
                        (get() as ApiServiceFactoryImpl).createService(),
                        get(),
                        get(),
                        get()
                    )
                }
                single { AdsGlobalEvent() }
                factory<ConnectionService> { ConnectionServiceImpl(get()) }
                single<AdCollectorDispatchers> { AdCollectorDispatchersRegular }

            }
        )
    }
}


