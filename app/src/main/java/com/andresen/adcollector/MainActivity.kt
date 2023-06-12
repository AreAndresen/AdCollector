package com.andresen.adcollector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresen.adcollector.main.navigation.AdCollectorNavHost
import com.andresen.feature_ads.viewmodel.AdsViewModel
import com.andresen.library_style.theme.AdCollectorComposableTheme
import com.andresen.library_style.theme.AdCollectorTheme
import com.andresen.adcollector.main.navigation.Screen
import com.andresen.feature_ads.mapper.AdsMapper
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    // todo these viewmodels move out of main
    private val adsViewModel: AdsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mapUiState by adsViewModel.state.collectAsState(AdsMapper.loading())
            //val unitsUiState by unitViewModel.state.collectAsState()

            AdCollectorComposableTheme {
                val navController = rememberNavController()
                val items = listOf(
                    Screen.Settings,
                    Screen.Ads,
                    Screen.More,
                )
                val scaffoldState = rememberScaffoldState()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier,
                    backgroundColor = AdCollectorTheme.colors.medium,
                    contentColor = AdCollectorTheme.colors.contrastLight,
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = AdCollectorTheme.colors.medium,
                            contentColor = AdCollectorTheme.colors.contrastLight,
                        ) {
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painter = when (screen.route) {
                                                "settings" -> painterResource(id = com.andresen.library_style.R.drawable.chat)
                                                "ads" -> painterResource(id = com.andresen.library_style.R.drawable.map)
                                                "more" -> painterResource(id = com.andresen.library_style.R.drawable.units)
                                                else -> painterResource(id = com.andresen.library_style.R.drawable.map)
                                            },
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    AdCollectorNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        adsViewModel = adsViewModel,
                    )
                }
            }
        }
        }
    }
