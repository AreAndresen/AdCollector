package com.andresen.libraryStyle.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresen.library_style.theme.AdCollectorComposableTheme
import com.andresen.library_style.theme.AdCollectorTheme

@Composable
fun AdCollectorTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    singleLine: Boolean = false,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    Box(
        modifier = modifier
            .background(
                color = AdCollectorTheme.colors.mediumLight10,
                shape = AdCollectorTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {

        var isTextFieldFocused by remember { mutableStateOf(false) }
        val showLabel = remember(isTextFieldFocused, text) {
            isTextFieldFocused || text.isNotEmpty()
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isTextFieldFocused = it.hasFocus },
                    value = text,
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    textStyle = AdCollectorTheme.typography.body.copy(color = AdCollectorTheme.colors.contrastLight),
                    cursorBrush = SolidColor(AdCollectorTheme.colors.contrastLight),
                    onValueChange = onTextChange,

                )
            }
        }

        AnimatedVisibility(
            visible = !showLabel,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = label,
                maxLines = 1,
                style = AdCollectorTheme.typography.body,
                color = AdCollectorTheme.colors.contrastLight70
            )
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

            AdCollectorTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = text,
                label = "Unit1",
                onTextChange = { text = it }
            )
        }
    }
}
