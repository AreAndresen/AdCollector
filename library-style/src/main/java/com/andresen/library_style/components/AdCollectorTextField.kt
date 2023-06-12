package com.andresen.libraryStyle.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
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



