package home.presentation

import home.presentation.models.UiHomeProduct

sealed class HomeEvent {
    data class OnProductClicked(val product: UiHomeProduct) : HomeEvent()
    data class OnRowEndReached(val type: String) : HomeEvent()
    data object OnColumnEndReached : HomeEvent()
    data class OnFavoriteClicked(val product: UiHomeProduct) : HomeEvent()
}