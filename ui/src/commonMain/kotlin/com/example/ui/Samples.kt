package com.example.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class SamplesItemState(
    val id: Int,
    val name: String,
    val isRevealed: Boolean
)

@Composable
fun SwipeSample(modifier: Modifier = Modifier) {

    val listTest = mutableListOf<SamplesItemState>()
    repeat(10) {
        listTest.add(
            SamplesItemState(
                id = it,
                name = "Test #$it",
                isRevealed = false
            )
        )
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(listTest, key = { it.id }) { book ->
            SwiepableItemWithActions(
                isRevealed = false,
                actions = {
                    ActionIcon(
                        imageVector = Icons.Default.Star,
                        backgroundColor = Color.Red,
                        onClick = {
//                            book.copy(
//                                isRevealed = false
//                            )
                        },
                    )
                    ActionIcon(
                        imageVector = Icons.Default.Delete,
                        backgroundColor = Color.Black,
                        onClick = { }
                    )
                }
            ) {
                Text(
                    text = "Test Item",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}