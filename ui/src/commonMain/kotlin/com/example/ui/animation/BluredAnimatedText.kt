package com.example.ui.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

private const val ANIMATION_DURATION = 1000

/**
*   A liner blurred animated text
*   Only useful for Android 12 above
*/

@Composable
fun BlurredAnimatedText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val blurList = text.mapIndexed { index, character ->
        if (character == ' ') {
            remember {
                mutableFloatStateOf(0f)
            }
        } else {
            val infiniteTransition =
                rememberInfiniteTransition(label = "infinite transition $index")
            infiniteTransition.animateFloat(
                initialValue = 10f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = ANIMATION_DURATION,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(
                        offsetMillis = (ANIMATION_DURATION / text.length) * index
                    )
                ),
                label = "blur animation"
            )
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        text.forEachIndexed { index, character ->
            Text(
                text = character.toString(),
                color = Color.White,
                modifier = Modifier
                    .graphicsLayer {
                        if (character != ' ') {
                            val blurAmount = blurList[index].value
                            renderEffect = BlurEffect(
                                radiusX = blurAmount,
                                radiusY = blurAmount
                            )
                        }
                    }
            )
        }
    }
}
