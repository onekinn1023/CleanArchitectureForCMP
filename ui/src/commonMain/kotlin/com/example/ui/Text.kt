package com.example.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Extension function for modifier to apply a privacy-sensitive effect when app enters the pause state
 * through recent menu.
 * @param effect Defaults `Redact(black)`
 */
@Composable
fun Modifier.privacySensitive(effect: PrivacyEffect = PrivacyEffect.Redact()): Modifier {
    val windowInfo = LocalWindowInfo.current
    val isInRecentApps by rememberUpdatedState(!windowInfo.isWindowFocused)

    return if (isInRecentApps) {
        when(effect) {
            is PrivacyEffect.Blur -> applyBlur(effect.blurRadius)
            is PrivacyEffect.Redact -> applyRedact(effect.color)
        }
    } else {
        this
    }
}

@Composable
private fun Modifier.applyRedact(color: Color) = drawWithContent {
    drawContent()
    drawRect(color)
}

/**
 *  TODO for blur  
 */
@Composable
private fun Modifier.applyBlur(radius: Dp): Modifier = applyRedact(Color.LightGray)

sealed class PrivacyEffect {
    data class Redact(val color: Color = Color.Black) : PrivacyEffect()
    data class Blur(val blurRadius: Dp = 15.dp) : PrivacyEffect()
}