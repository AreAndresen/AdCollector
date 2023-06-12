package com.andresen.library_repositories.ads.remote

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow

class AdsGlobalEvent {

    private val adUpdate = BroadcastChannel<String>(1)

    suspend fun adUpdate() {
        adUpdate.send("Update")
    }

    fun adUpdateListener() = adUpdate.asFlow()
}