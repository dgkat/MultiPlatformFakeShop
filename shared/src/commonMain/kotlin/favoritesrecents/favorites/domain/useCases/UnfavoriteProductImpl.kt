package favoritesrecents.favorites.domain.useCases

import favoritesrecents.favorites.domain.repository.FavoritesRepository
import favoritesrecents.sharedDomain.UpdateFavoriteRecentProduct

class UnfavoriteProductImpl(private val favoritesRepository: FavoritesRepository) :
    UpdateFavoriteRecentProduct {
    override suspend fun invoke(productId: Int) {
        favoritesRepository.unfavoriteProduct(productId)
    }
}