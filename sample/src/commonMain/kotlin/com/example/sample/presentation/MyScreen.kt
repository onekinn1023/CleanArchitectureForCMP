package com.example.sample.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.common.ObserveAsEvent
import com.example.sample.navigation.MyScreenComponent
import com.example.sample.presentation.viewmodels.MyAction
import com.example.sample.presentation.viewmodels.MyEvent
import com.example.sample.presentation.viewmodels.MyState
import com.example.sample.presentation.viewmodels.MyViewModel
import com.example.ui.CustomSearchView
import com.example.ui.SimpleToolbar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    myScreenComponent: MyScreenComponent? = null,
    isDecomposeTheme: Boolean = false
) {
    val viewModel = koinViewModel<MyViewModel>()
    val state by viewModel.myState.collectAsState()
    ObserveAsEvent(viewModel.event) {
        when (it) {
            MyEvent.FirstEvent -> {
                println("Effect 1")
            }

            MyEvent.NavigateToB -> {
                myScreenComponent?.onAction(
                    MyScreenComponent.MyScreenAction.NavigateToNext(
                        state.exampleLocalText
                    )
                )
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        DemoScreen(
            modifier = modifier.fillMaxSize(),
            state = state,
            onAction = viewModel::onAction,
            isDecomposeTheme = isDecomposeTheme
        )
    }
}

@Composable
fun DemoScreen(
    modifier: Modifier = Modifier,
    state: MyState,
    onAction: (MyAction) -> Unit,
    isDecomposeTheme: Boolean
) {
    Column(
        modifier = modifier
    ) {
        SimpleToolbar(title = "Main Page")
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = state.initialText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            DemoRequestApiOperation(
                modifier = Modifier.fillMaxWidth(),
                queriedText = state.exampleNetText,
                onSearch = { onAction(MyAction.GetRemoteString(it)) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color.Black))
            Text(text = state.exampleLocalText)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onAction(MyAction.ChangeText)
                }
            ) {
                Text(text = "Change local text")
            }
            if (isDecomposeTheme) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        onAction(MyAction.ClickNavigateButton)
                    }
                ) {
                    Text(text = "Navigate to next screen B!")
                }
            }
        }
    }

}

@Composable
fun DemoRequestApiOperation(
    modifier: Modifier = Modifier,
    queriedText: String,
    onSearch: (String) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomSearchView(
            modifier = Modifier.fillMaxWidth(),
            onSearch = onSearch
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = queriedText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
