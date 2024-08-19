import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin
import platform.Foundation.NSFileManager

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}