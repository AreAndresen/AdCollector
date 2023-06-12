package com.andresen.adcollector.main.navigation

import androidx.annotation.StringRes
import com.andresen.adcollector.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    object Ads : Screen("ads", com.andresen.library_style.R.string.ads)
    object Settings : Screen("settings", com.andresen.library_style.R.string.settings)

    object More : Screen("more", com.andresen.library_style.R.string.more)
}