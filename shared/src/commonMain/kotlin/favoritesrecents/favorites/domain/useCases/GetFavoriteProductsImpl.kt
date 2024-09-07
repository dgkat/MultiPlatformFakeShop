package favoritesrecents.favorites.domain.useCases

import core.domain.models.Product
import favoritesrecents.favorites.domain.repository.FavoritesRepository
import favoritesrecents.sharedDomain.GetFavoriteRecentProducts
import kotlinx.coroutines.flow.Flow

class GetFavoriteProductsImpl(
    private val favoritesRepository: FavoritesRepository
): GetFavoriteRecentProducts {
    override suspend fun invoke(): Flow<List<Product>> {
        return favoritesRepository.getFavoriteProducts()
    }
}