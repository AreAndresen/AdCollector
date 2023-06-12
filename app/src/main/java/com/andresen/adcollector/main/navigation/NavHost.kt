package com.andresen.adcollector.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.feature_ads.view.AdsScreen
import com.andresen.feature_ads.viewmodel.AdsViewModel

@Composable
fun AdCollectorNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "ads",
    adsViewModel: AdsViewModel = viewModel(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("settings") {
            // todo
        }
        composable("ads") {
            AdsScreen(
                modifier = modifier,
                viewModel = adsViewModel,
                onFavouriteAdClick = { ad ->
                    adsViewModel.favouriteAd(ad)
                }
            )
        }
        composable("more") {
            // todo
        }
    }
}