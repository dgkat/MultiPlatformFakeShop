package favoritesrecents.favorites.domain.useCases

import favoritesrecents.sharedDomain.UpdateFavoriteRecentProduct

class UnfavoriteProductImpl() : UpdateFavoriteRecentProduct {
    override suspend fun invoke(productId: Int) {
        //TODO remove from faves and add to recently viewed
    }
}