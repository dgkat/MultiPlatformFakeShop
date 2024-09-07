package favoritesrecents.sharedPresentation

sealed class FavoritesRecentsEvent {
    data class OnProductClicked(val productId: Int) : FavoritesRecentsEvent()
    data class OnFavoriteClicked(val productId: Int) : FavoritesRecentsEvent()
}