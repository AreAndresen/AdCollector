package com.andresen.feature_ads.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andresen.feature_ads.R
import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsContentUi
import com.andresen.feature_ads.view.composable.SearchBarCompose
import com.andresen.feature_ads.viewmodel.AdsViewModel
import com.andresen.library_style.theme.AdCollectorTheme


@Composable
fun AdsScreen(
    modifier: Modifier = Modifier,
    viewModel: AdsViewModel,
    onFavouriteAdClick: (AdUiModel) -> Unit = { },
) {
    val adsUiState by viewModel.state.collectAsState()
    val state = adsUiState.adsContent
    val searchText = adsUiState.adsTopSearchBar.query
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        topBar = {
            SearchBarCompose(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                searchText = searchText,
                onSearchClick = { search ->
                    viewModel.onSearchClick(search)
                },
                onClearSearch = {
                    viewModel.onClearSearch()
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is AdsContentUi.Loading -> {} // todo LoadingScreen(modifier)
                is AdsContentUi.AdsContent -> {
                    UnitsGridScreen(state.ads)
                }

                is AdsContentUi.Error -> {} //todo ErrorScreen(retryAction, modifier)
            }
        }
    }
}

@Composable
fun UnitsGridScreen(
    units: List<AdUiModel>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(units.size) { index ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomCenter) {
                    AdPhotoCard(units[index])
                    Icon(
                        modifier = Modifier.size(150.dp),
                        painter = painterResource(com.andresen.library_style.R.drawable.chat),
                        contentDescription = null,
                        tint = AdCollectorTheme.colors.mediumLight10
                    )
                }
                Text(
                    text = stringResource(id = com.andresen.library_style.R.string.unit_id, units[index].id),
                    textAlign = TextAlign.Center,
                    color = AdCollectorTheme.colors.mediumLight10,
                    style = AdCollectorTheme.typography.title2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun AdPhotoCard(ad: AdUiModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(ad.imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(com.andresen.library_style.R.drawable.error_img),
            placeholder = painterResource(com.andresen.library_style.R.drawable.chat),
            contentDescription = stringResource(com.andresen.library_style.R.string.unit_id),
            contentScale = ContentScale.FillBounds,
        )
    }
}


