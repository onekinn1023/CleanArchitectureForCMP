package com.example.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
*    自适应size的仿google预览点击进行尺寸动画的按钮
* */
@Composable
fun RowScope.ExpressiveFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    defaultWeight: Float,
    isChecked: Boolean,
    checkedColor: Color,
    defaultColor: Color,
    onClick: () -> Unit = {},
    defaultSize: Dp = 70.dp
) {
    var shapeSelected by remember { mutableStateOf(false) }
    val animatedRadius by animateDpAsState(
        targetValue = takeIf { shapeSelected }?.let { 6.dp } ?: 16.dp,
        label = "animatedRadius"
    )
    val animatedWeight by animateFloatAsState(
        targetValue = takeIf { shapeSelected }?.let { 0.25f } ?: 0f
    )

    val color by remember {
        derivedStateOf { if (shapeSelected || isChecked) checkedColor else defaultColor }
    }

    val buttonWeight by remember { derivedStateOf { animatedWeight + defaultWeight } }

    IconButton(
        modifier = modifier
            .padding(4.dp)
            .weight(buttonWeight)
            .height(defaultSize)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Main)
                        event.changes.forEach {
                            shapeSelected = it.pressed
                        }
                    }
                }
            },
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = color,
            contentColor = color
        ),

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}