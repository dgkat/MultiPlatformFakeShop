package favoritesrecents.recents.domain.useCases

import favoritesrecents.sharedDomain.UpdateFavoriteRecentProduct

class UpdateFavoriteStatus : UpdateFavoriteRecentProduct {
    override suspend fun invoke(productId: Int) {
        //TODO remove from
    }
}