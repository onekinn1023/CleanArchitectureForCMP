package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val borderColor = Color(0x66222222)
val defaultColor = Color(0x66ffffff)

@Composable
fun TabBoxAnimation(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        MainBoxSurface(
            modifier = Modifier.fillMaxSize(),
        ) {
            RowBox()
        }
        Text(
            text = "Score",
            modifier = Modifier
                .padding(15.dp)
                .align(
                    Alignment.TopEnd
                ),
            style = MaterialTheme.typography.titleMedium
        )
    }

}

@Composable
fun MainBoxSurface(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    content: @Composable () -> Unit,
) {
//    LaunchedEffect(Unit)  {
//        while (isActive) {
//            listState.scrollBy(4f)
//            delay(20)
//        }
//    }

    LazyColumn(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Yellow, Color.Blue)
            )
        ),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            repeat(100) {
                content()
            }
        }
    }
}

@Composable
fun RowBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val randomIndex by remember { mutableIntStateOf((0..3).random()) }
    val height = 160.dp
    Row(
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .border(
                        width = 0.5.dp,
                        color = borderColor
                    )
                    .background(
                        color = if (index == randomIndex) {
                            borderColor
                        } else defaultColor
                    )
                    .clickable {
                        onClick()
                    }
            )
        }
    }
}