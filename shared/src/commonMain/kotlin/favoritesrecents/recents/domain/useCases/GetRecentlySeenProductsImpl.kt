package favoritesrecents.recents.domain.useCases

import core.domain.models.Product
import favoritesrecents.recents.domain.repository.RecentsRepository
import favoritesrecents.sharedDomain.GetFavoriteRecentProducts
import kotlinx.coroutines.flow.Flow

class GetRecentlySeenProductsImpl(
    private val recentsRepository: RecentsRepository
) : GetFavoriteRecentProducts {
    override suspend fun invoke(): Flow<List<Product>> {
        return recentsRepository.getRecentlySeenProducts()
    }
}