package com.andresen.feature_ads.view.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.andresen.library_style.components.AdCollectorTextSearchField

@Composable
fun SearchBarCompose(
    modifier: Modifier = Modifier,
    searchText: String?,
    onTextChange: (String) -> Unit = { },
    onClearSearch: () -> Unit = { },
    onToggleShowFavourites: () -> Unit = { },
    showFavourites: Boolean
) {

    Box {
        AdCollectorTextSearchField(
            modifier = Modifier.fillMaxWidth(),
            searchText = searchText.orEmpty(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onTextChange = onTextChange,
            onClearClick = onClearSearch,
            onToggleShowFavourites = onToggleShowFavourites,
            showFavourites = showFavourites
        )
    }
}

