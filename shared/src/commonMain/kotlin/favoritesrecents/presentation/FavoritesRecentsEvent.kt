package favoritesrecents.presentation

sealed class FavoritesRecentsEvent {
    data class OnProductClicked(val productId: Int) : FavoritesRecentsEvent()
}