package com.andresen.library_style.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LocalAdCollectorShapes = staticCompositionLocalOf<AdCollectorShapes> {
    error("LocalShapes not initialized!")
}

data class AdCollectorShapes(
    val small: Shape,
    val medium: Shape,
    val large: Shape
)

internal fun createAdCollectorShapes() = AdCollectorShapes(
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(6.dp)
)