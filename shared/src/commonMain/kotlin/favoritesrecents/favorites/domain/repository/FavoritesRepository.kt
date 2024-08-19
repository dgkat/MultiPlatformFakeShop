package favoritesrecents.favorites.domain.repository

import core.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoriteProducts(): Flow<List<Product>>
}