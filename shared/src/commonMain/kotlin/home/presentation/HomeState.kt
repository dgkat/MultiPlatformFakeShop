package home.presentation


data class HomeState (
    val homeRowStates : List<HomeRowState> = emptyList(),
    val loading : Boolean = true
)