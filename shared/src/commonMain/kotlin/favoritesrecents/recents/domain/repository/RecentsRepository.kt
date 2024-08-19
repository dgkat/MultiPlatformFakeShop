package favoritesrecents.recents.domain.repository

import core.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface RecentsRepository {

    fun getRecentlySeenProducts(): Flow<List<Product>>
}