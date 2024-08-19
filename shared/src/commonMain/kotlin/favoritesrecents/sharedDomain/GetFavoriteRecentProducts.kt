package favoritesrecents.sharedDomain

import core.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface GetFavoriteRecentProducts {
    suspend operator fun invoke(): Flow<List<Product>>
}