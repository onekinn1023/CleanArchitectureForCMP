package com.example.ui.sample

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SampleScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    fabColor: Color,
    fabAction: () -> Unit
) {

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = fabAction,
                containerColor = fabColor,
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("Unique Key for sharing animation with other screen"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Sample Text")
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SampleShared(
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout {
        // nav here
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            modifier = modifier.fillMaxSize(),
            startDestination = "/Sample"
        ) {
            composable("/Sample") {
                SampleScreen(
                    modifier = modifier.fillMaxSize(),
                    fabColor = Color.Red,
                    fabAction = {
                        navController.navigate("/Shared")
                    },
                    animatedVisibilityScope = this
                )
            }
            composable("/Shared") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                "Unique Key for sharing animation with other screen"
                            ),
                            animatedVisibilityScope = this
                        )
                )
            }
        }

    }
}