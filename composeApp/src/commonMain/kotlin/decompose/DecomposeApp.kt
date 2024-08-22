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
import org.koin.dsl.koinApplication

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
                when(val child = it.instance) {
                    is RootComponent.Child.ScreenA -> {
                        ScreenA(modifier = Modifier, screenAComponent = child.screenAComponent)
                    }

                    is RootComponent.Child.ScreenB -> {
                        ScreenB(modifier = Modifier, screenBComponent = child.screenBComponent)
                    }
                }
            }
        }

    }
}