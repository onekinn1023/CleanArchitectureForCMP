package com.example.ui.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

internal val LightColorThemes = lightColorScheme(
    primary = Primary,
    surface = Surface,
    surfaceContainerLowest = SurfaceLowest,
    background = Background,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant
)

// extended color to adapt the dark and light theme manually
@Composable
fun extendedColor(light: Color, dark: Color): Color {
    return if (isSystemInDarkTheme()) dark else light
}

internal val ColorScheme.extraColor: Color
    @Composable get() = extendedColor(
        light = Color(0xFF000000),
        dark = Color(0xFFFFFFFF)
    )

internal val CMPShapes = Shapes(
    extraSmall = RoundedCornerShape(5.dp),
    medium = RoundedCornerShape(15.dp)
)

@Composable
fun CMPTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorThemes,
        typography = CMPTypography,
        shapes = CMPShapes,
        content = content
    )
}