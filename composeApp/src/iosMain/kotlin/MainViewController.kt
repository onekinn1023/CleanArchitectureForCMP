import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import decompose.AppRootComponent
import decompose.DecomposeMaterialApp
import di.initKoin
import platform.Foundation.NSFileManager

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {

    App()
//    val root = remember {
//        AppRootComponent(
//            DefaultComponentContext(LifecycleRegistry())
//        )
//    }
//    DecomposeMaterialApp(root)
}