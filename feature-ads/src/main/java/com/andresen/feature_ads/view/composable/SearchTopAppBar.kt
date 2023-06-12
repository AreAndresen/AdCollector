package com.andresen.feature_ads.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andresen.feature_ads.R
import com.andresen.library_style.extensions.rememberStringResource
import com.andresen.library_style.theme.AdCollectorTheme

@Composable
fun SearchBarCompose(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchClick: (String) -> Unit = { },
    onClearSearch: () -> Unit = { },
) {
    Box(
        modifier = modifier
            .background(
                color = AdCollectorTheme.colors.mediumLight10,
                shape = AdCollectorTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        var value by remember { mutableStateOf(searchText) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = value,
                onValueChange = { value = it },
                placeholder = {
                    Text(
                        text = rememberStringResource(id = com.andresen.library_style.R.string.map_edit),
                        style = AdCollectorTheme.typography.body,
                        color = AdCollectorTheme.colors.contrastLight70
                    )
                },
                maxLines = 1,
                textStyle = AdCollectorTheme.typography.body.copy(color = AdCollectorTheme.colors.contrastLight),
                trailingIcon = {
                    IconButton(onClick = {
                        value = "" // todo check why viewmodel isnt updating this to empty
                        onClearSearch()
                    }) {
                        Icon(
                            painter = painterResource(id =  com.andresen.library_style.R.drawable.x_remove),
                            contentDescription = null,
                            tint = AdCollectorTheme.colors.contrastLight
                        )
                    }
                }
            )
            IconButton(onClick = { onSearchClick(value) }) {
                Icon(
                    painter = painterResource(id =  com.andresen.library_style.R.drawable.search),
                    contentDescription = null,
                    tint = AdCollectorTheme.colors.contrastLight
                )
            }
        }
    }

}

