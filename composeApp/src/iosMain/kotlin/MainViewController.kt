import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.sample.SampleApp
import com.example.sample.navigation.RootComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun MainViewController() = ComposeUIViewController {
//    App()
    val root = remember {
        ManualKoinClass.rootComponentFactory(DefaultComponentContext(LifecycleRegistry()))
    }
    SampleApp(root)
}

object ManualKoinClass: KoinComponent {
    val rootComponentFactory: RootComponent.Factory by inject<RootComponent.Factory>()
}