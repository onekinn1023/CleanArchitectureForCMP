package example.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.utils.MessageDescription

@Composable
@MessageDescription(message = "It will throw unsolved text2 error while using BasicTextField2")
fun CustomSearchView(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {
    var queriedText by remember {
        mutableStateOf("")
    }
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .background(color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                modifier = Modifier.clickable {
                    onSearch(queriedText)
                },
                contentDescription = "search",
            )
            BasicTextField(
                value = queriedText,
                onValueChange = {
                    queriedText = it
                }
            )
        }

        AnimatedVisibility(visible = queriedText.isNotBlank()) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete text",
                modifier = Modifier.clickable {
                    queriedText = ""
                }
            )
        }
    }
}