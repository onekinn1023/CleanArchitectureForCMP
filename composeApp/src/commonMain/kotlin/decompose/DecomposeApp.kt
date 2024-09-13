package decompose

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.koin.compose.KoinContext
import example.presentation.MyScreen
import fileSystem.presentation.FileUploadScreen

@Composable
fun DecomposeMaterialApp(rootComponent: RootComponent) {
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
                        MyScreen(
                            modifier = Modifier,
                            myScreenComponent = child.myScreenComponent,
                            isDecomposeTheme = true
                        )
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