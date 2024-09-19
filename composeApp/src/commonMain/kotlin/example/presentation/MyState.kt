package example.presentation

data class MyState(
    val initialText: String = "",
    val exampleNetText: String = "",
    val exampleLocalText: String = "",
    val isLoading: Boolean = false
)
