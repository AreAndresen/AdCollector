package com.andresen.library_style.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresen.library_style.R
import com.andresen.library_style.extensions.rememberStringResource
import com.andresen.library_style.theme.AdCollectorComposableTheme
import com.andresen.library_style.theme.AdCollectorTheme

@Composable
fun AdCollectorTextSearchField(
    modifier: Modifier = Modifier,
    searchText: String,
    onTextChange: (String) -> Unit,
    onClearClick: () -> Unit = { },
    onGetLocalFavourites: () -> Unit = {},
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    Box(
        modifier = modifier
            .height(52.dp)
            .background(
                color = AdCollectorTheme.colors.mediumLight10,
                shape = AdCollectorTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        var isTextFieldFocused by remember { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isTextFieldFocused = it.hasFocus },
                    value = searchText,
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    textStyle = AdCollectorTheme.typography.body.copy(color = AdCollectorTheme.colors.contrastLight),
                    cursorBrush = SolidColor(AdCollectorTheme.colors.contrastLight),
                    onValueChange = { onTextChange(it) }
                )
            }
        }
        AnimatedTextFieldContent(
            label = rememberStringResource(id = R.string.search_title),
            searchText = searchText,
            isTextFieldFocused = isTextFieldFocused,
            onClearClick = onClearClick,
            onGetLocalFavourites = onGetLocalFavourites
        )
    }
}

@Composable
private fun AnimatedTextFieldContent(
    label: String,
    searchText: String,
    isTextFieldFocused: Boolean,
    onClearClick: () -> Unit = {},
    onGetLocalFavourites: () -> Unit = {},
) {
    val showLabel = remember(isTextFieldFocused, searchText) {
        !isTextFieldFocused && searchText.isEmpty()
    }
    val showClearButton = remember(isTextFieldFocused, searchText) {
        isTextFieldFocused && searchText.isNotEmpty()
    }
    val showPlaceholder = remember(isTextFieldFocused, searchText) {
        isTextFieldFocused && searchText.isEmpty()
    }

    AnimatedVisibility(
        visible = showLabel,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                tint = AdCollectorTheme.colors.contrastLight
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                maxLines = 1,
                style = AdCollectorTheme.typography.body,
                color = AdCollectorTheme.colors.contrastLight
            )
        }
    }

    AnimatedVisibility(
        visible = showClearButton,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { onClearClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_remove),
                        contentDescription = null,
                        tint = AdCollectorTheme.colors.contrastLight
                    )
                }
            }

        }
    }

    AnimatedVisibility(
        visible = !showClearButton,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { onGetLocalFavourites() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_favourite),
                        contentDescription = rememberStringResource(id = R.string.see_favourites),
                        tint = AdCollectorTheme.colors.contrastLight
                    )
                }
            }

        }
    }

    AnimatedVisibility(
        visible = showPlaceholder,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = label,
                    maxLines = 1,
                    style = AdCollectorTheme.typography.body,
                    color = AdCollectorTheme.colors.contrastLight70
                )
            }

        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    AdCollectorComposableTheme {
        Surface(
            color = AdCollectorTheme.colors.medium,
            contentColor = AdCollectorTheme.colors.contrastLight
        ) {
            var text by remember { mutableStateOf("") }

            AdCollectorTextSearchField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                searchText = text,
                onTextChange = { text = it },
                onClearClick = { }
            )
        }
    }
}
