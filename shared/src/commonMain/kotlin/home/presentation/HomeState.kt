package home.presentation

import kotlinx.coroutines.flow.StateFlow

data class HomeState (
    val data : List<StateFlow<HomeRowState>> = emptyList(),
    val loading : Boolean = true
)