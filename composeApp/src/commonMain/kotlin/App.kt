import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.core.annotation.KoinExperimentalAPI
import example.presentation.MyScreen
import example.presentation.RequirePermissionScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val controller = bindEffect()
            NavHost(
                navController = rememberNavController(),
                startDestination = "home"
            ) {
                composable("home") {
                   MyScreen(modifier = Modifier)
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
