package com.example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleToolbar(
    modifier: Modifier = Modifier,
    title: String,
    onBackAction: (() -> Unit)? = null
) {

    Column(
        modifier = modifier
    ) {
        Row {
            if (onBackAction != null) {
                Box(
                    modifier = Modifier.padding(5.dp)
                        .clickable {
                            onBackAction()
                        }
                        .background(
                            color = Color.Unspecified,
                            shape = RoundedCornerShape(3.dp)
                        )
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "arrow back",
                        modifier = Modifier.background(Color.Black)
                    )
                }
            }
            Text(
                text = title,
                fontSize = 30.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
        }
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color.Black))
    }
}