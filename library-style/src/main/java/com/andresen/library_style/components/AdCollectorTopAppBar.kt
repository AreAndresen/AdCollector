package com.andresen.library_style.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.andresen.library_style.R
import com.andresen.library_style.modifiers.noIndicationClickable
import com.andresen.library_style.theme.AdCollectorTheme

@Composable
fun TopAppBarComposable(
    isNightVision: Boolean,
    onToggleNightVision: () -> Unit
) {
    OverwatchTopAppBar(
        title = "",
        navigationIcon = {
            IconButton(onClick = { onToggleNightVision() }) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = stringResource(id = R.string.map_edit)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onToggleNightVision()
                },
                enabled = true
            ) {
                Icon(
                    painter = if (isNightVision) {
                        painterResource(id = R.drawable.visibility_on)
                    } else painterResource(id = R.drawable.visibility_off),
                    contentDescription = stringResource(id = R.string.map_nightvision_toggle_desc)
                )
            }
        }
    )
}

@Composable
private fun OverwatchTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    elevation: Dp = 0.dp,
    backgroundColor: Color = Color.Transparent,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = { },
    onTitleClick: () -> Unit = { }
) {
    TopAppBar(
        modifier = modifier.noIndicationClickable { onTitleClick() },
        title = {
            Text(
                text = title,
                style = AdCollectorTheme.typography.headline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = AdCollectorTheme.colors.contrastLight
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        elevation = elevation,
    )
}