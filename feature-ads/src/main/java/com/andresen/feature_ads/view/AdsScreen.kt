package com.andresen.feature_ads.view

import androidx.compose.foundation.clickable
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
import androidx.compose.material.IconButton
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
import com.andresen.feature_ads.model.AdUiModel
import com.andresen.feature_ads.model.AdsContentUi
import com.andresen.feature_ads.view.composable.SearchBarCompose
import com.andresen.feature_ads.viewmodel.AdsViewModel
import com.andresen.library_style.R
import com.andresen.library_style.theme.AdCollectorTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun AdsScreen(
    modifier: Modifier = Modifier,
    viewModel: AdsViewModel = koinViewModel()
) {
    val adsUiState by viewModel.state.collectAsState()
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
                searchText = adsUiState.adsTopSearchBar.query,
                onTextChange = { search ->
                    viewModel.onSearchChange(search)
                },
                onClearSearch = {
                    viewModel.onClearSearch()
                },
                onToggleShowFavourites = {
                    viewModel.onToggleShowFavourites()
                },
                showFavourites = adsUiState.adsTopSearchBar.showFavourites
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
            when (val state = adsUiState.adsContent) {
                is AdsContentUi.Loading -> {
                    // todo LoadingScreen(modifier)
                }

                is AdsContentUi.AdsContent -> {
                    AdsGridScreen(
                        state.ads.items,
                        onFavouriteAdClick = { ad ->
                            viewModel.addFavouriteAd(ad)
                        }
                    )
                }

                is AdsContentUi.Error -> {
                    //todo ErrorScreen(retryAction, modifier)
                }
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

                    IconButton(onClick = { onFavouriteAdClick(ads[index]) }) {
                        Icon(
                            modifier = Modifier.size(150.dp),
                            painter = if (ads[index].isFavourite) {
                                painterResource(id = R.drawable.star_favourite)
                            } else painterResource(id = R.drawable.star_not_favourite),
                            contentDescription = null,
                            tint = AdCollectorTheme.colors.contrastLight
                        )
                    }
                }

                Text(
                    text = stringResource(
                        id = R.string.price,
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
                        id = R.string.title,
                        ads[index].title
                    ),
                    textAlign = TextAlign.Center,
                    color = AdCollectorTheme.colors.mediumLight10,
                    style = AdCollectorTheme.typography.title3,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = stringResource(
                        id = R.string.location,
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
fun AdPhotoCard(
    ad: AdUiModel,
    modifier: Modifier = Modifier,
    onFavouriteAdClick: (AdUiModel) -> Unit = { },
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp,
    ) {
        AsyncImage(
            modifier = Modifier.clickable {
                onFavouriteAdClick(ad)
            },
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(ad.image?.imageUrl)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.error_img),
            placeholder = painterResource(R.drawable.downloading),
            contentDescription = stringResource(R.string.price),
            contentScale = ContentScale.FillBounds,
        )
    }
}


