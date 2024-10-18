package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.arkivanov.decompose.retainedComponent
import com.example.sample.SampleApp
import com.example.sample.navigation.RootComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isChecking = false
        lifecycleScope.launch {
            delay(200L)
            isChecking = true
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isChecking
            }
        }
        val rootComponentFactory by inject<RootComponent.Factory>()
        val rootComponent = retainedComponent {
            rootComponentFactory(it)
        }
        setContent {
            SampleApp(rootComponent = rootComponent)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
}