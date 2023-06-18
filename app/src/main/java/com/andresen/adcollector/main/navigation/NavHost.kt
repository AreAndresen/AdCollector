package com.andresen.adcollector.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.feature_ads.view.AdsScreen

@Composable
fun AdCollectorNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "ads",
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
                modifier = modifier
            )
        }
        composable("more") {
            // todo
        }
    }
}