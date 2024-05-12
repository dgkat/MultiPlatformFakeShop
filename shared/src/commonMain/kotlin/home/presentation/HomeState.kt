package home.presentation

data class HomeState (
    val data : Map<String,HomeRowState>  = emptyMap(),
    val loading : Boolean = true
)