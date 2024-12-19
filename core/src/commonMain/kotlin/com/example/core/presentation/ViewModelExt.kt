package com.example.core.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.core.utils.MessageDescription
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.compose.viewmodel.koinViewModel

/**
 *  @param NavBackStackEntry viewModelStoreOwner implementation
 * @sample SharedViewModelSample
 */

@Composable
@MessageDescription("Fetch shared viewModel according to parent navigation graph through koin")
inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val parentRoute = destination.parent?.route ?: return koinViewModel<T>()
    val entry = remember(this) {
        navController.getBackStackEntry(parentRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = entry
    )
}

internal class TestViewModel : ViewModel() {

    private val _state = MutableStateFlow(TestState())
    val state = _state.asStateFlow()

    data class TestState(
        val id: Int = 0,
        val name: String = ""
    )
}

@Composable
private fun SharedViewModelSample() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.ScreenRootGraph
    ) {
        navigation<Route.ScreenRootGraph>(
            startDestination = Route.ScreenA
        ) {
            composable<Route.ScreenA>(
                enterTransition = { slideInHorizontally() + fadeIn(animationSpec = tween(100)) }
            ) { entry ->
                val sharedViewModel = entry.sharedKoinViewModel<TestViewModel>(navController)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Text()
                }
            }
            composable<Route.ScreenB>(
                enterTransition = { slideInHorizontally() + fadeIn(animationSpec = tween(100)) }
            ) { entry ->
                val sharedViewModel = entry.sharedKoinViewModel<TestViewModel>(navController)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Text()
                }
            }
        }
    }
}

sealed interface Route {
    data object ScreenRootGraph : Route
    data object ScreenA : Route
    data object ScreenB : Route
}