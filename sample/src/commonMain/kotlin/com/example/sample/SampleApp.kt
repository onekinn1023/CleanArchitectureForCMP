package com.example.sample

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.sample.navigation.RootComponent
import com.example.sample.presentation.FileUploadScreen
import com.example.sample.presentation.RequirePermissionScreen
import com.example.sample.presentation.ScreenB
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun SampleApp(
    rootComponent: RootComponent
) {
    MaterialTheme {
        KoinContext {
            val childStack by rootComponent.stack.subscribeAsState()
            Children(
                stack = childStack,
                modifier = Modifier,
                animation = stackAnimation(fade())
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.MyScreen -> {
//                        MyScreen(
//                            modifier = Modifier,
//                            myScreenComponent = child.myScreenComponent,
//                            isDecomposeTheme = true
//                        )
                    }

                    is RootComponent.Child.ScreenB -> {
                        ScreenB(modifier = Modifier, screenBComponent = child.screenBComponent)
                    }

                    is RootComponent.Child.UploadFileScreen -> {
                        FileUploadScreen(
                            modifier = Modifier,
                            uploadFileScreenComponent = child.uploadFileScreenComponent
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun NavigationComponentTheme() {
    MaterialTheme {
        KoinContext {
            val controller = bindEffect()
            NavHost(
                navController = rememberNavController(),
                startDestination = "home"
            ) {
                composable("home") {
//                    MyScreen(modifier = Modifier)
                }
                composable("permission") {
                    RequirePermissionScreen(modifier = Modifier, controller = controller)
                }
            }
        }
    }
}

@Composable
private fun bindEffect(): PermissionsController {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }
    BindEffect(controller)
    return controller
}