package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    backgroundColor: Color,
    contentDescription: String? = null,
    tint: Color = Color.White,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}