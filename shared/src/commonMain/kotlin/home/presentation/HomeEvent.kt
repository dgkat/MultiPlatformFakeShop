package home.presentation

sealed class HomeEvent {
    data class OnProductClicked(val productId: String? = null) : HomeEvent()
}