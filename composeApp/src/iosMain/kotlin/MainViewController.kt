import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import decompose.AppRootComponent
import decompose.DecomposeMaterialApp
import decompose.RootComponent
import di.initKoin
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSFileManager

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {

//    App()
    val root = remember {
        ManualKoinClass.rootComponentFactory(DefaultComponentContext(LifecycleRegistry()))
    }
    DecomposeMaterialApp(root)
}

object ManualKoinClass: KoinComponent {
    val rootComponentFactory: RootComponent.Factory by inject<RootComponent.Factory>()
}