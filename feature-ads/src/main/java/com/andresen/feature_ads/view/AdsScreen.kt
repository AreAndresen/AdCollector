package com.andresen.feature_ads.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
        modifier = Modifier.padding(
            top = 16.dp
        ),
        scaffoldState = scaffoldState,
        topBar = {
            SearchBarCompose(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                searchText = searchText,
                onTextChange = { search ->
                    viewModel.onTextChange(search)
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
                    AdsGridScreen(
                        state.ads.items,
                        onFavouriteAdClick
                    )
                }

                is AdsContentUi.Error -> {} //todo ErrorScreen(retryAction, modifier)
            }
        }
    }
}

@Composable
fun AdsGridScreen(
    ads: List<AdUiModel>,
    onFavouriteAdClick: (AdUiModel) -> Unit = { },
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(ads.size) { index ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomCenter) {
                    AdPhotoCard(ads[index])
                }

                Text(
                    text = stringResource(
                        id = com.andresen.library_style.R.string.price,
                        ads[index].price.toString()
                    ),
                    textAlign = TextAlign.Center,
                    color = AdCollectorTheme.colors.mediumLight10,
                    style = AdCollectorTheme.typography.title1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        id = com.andresen.library_style.R.string.title,
                        ads[index].title.toString()
                    ),
                    textAlign = TextAlign.Center,
                    color = AdCollectorTheme.colors.mediumLight10,
                    style = AdCollectorTheme.typography.title3,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        id = com.andresen.library_style.R.string.location,
                        ads[index].location.toString()
                    ),
                    textAlign = TextAlign.Center,
                    color = AdCollectorTheme.colors.mediumLight10,
                    style = AdCollectorTheme.typography.body,
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
                .data(ad.image?.imageUrl)
                .crossfade(true)
                .build(),
            error = painterResource(com.andresen.library_style.R.drawable.error_img),
            placeholder = painterResource(com.andresen.library_style.R.drawable.downloading),
            contentDescription = stringResource(com.andresen.library_style.R.string.price),
            contentScale = ContentScale.FillBounds,
        )
    }
}


