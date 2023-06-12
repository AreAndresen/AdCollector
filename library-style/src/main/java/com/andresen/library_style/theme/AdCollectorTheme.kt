package com.andresen.library_style.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable


@Composable
fun AdCollectorComposableTheme(
    adCollectorColors: AdCollectorColors = createBaseBlueColors(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides adCollectorColors,
        LocalAdCollectorTypography provides createAdCollectorSansTypography(),
        LocalAdCollectorShapes provides createAdCollectorShapes(),

        LocalIndication provides rememberRipple(),

        content = { content() }
    )
}

object AdCollectorTheme {
    val colors: AdCollectorColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: AdCollectorTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAdCollectorTypography.current

    val shapes: AdCollectorShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAdCollectorShapes.current
}