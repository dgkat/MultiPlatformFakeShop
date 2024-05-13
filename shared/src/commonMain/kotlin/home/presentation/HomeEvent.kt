package home.presentation

sealed class HomeEvent {
    data class OnProductClicked(val productId: String? = null) : HomeEvent()
    data class OnRowEndReached(val type: String?) : HomeEvent()
    data object OnColumnEndReached : HomeEvent()
}