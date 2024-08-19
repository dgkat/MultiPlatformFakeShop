package favoritesrecents.recents.data

import core.data.local.ProductsDao
import core.data.mappers.LocalToDomainProductMapper
import core.domain.models.Product
import favoritesrecents.recents.domain.repository.RecentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentsRepositoryImpl(
    private val productDao: ProductsDao,
    private val localToDomainProductMapper: LocalToDomainProductMapper,
) : RecentsRepository {
    override fun getRecentlySeenProducts(): Flow<List<Product>> {
        return productDao.getRecentlySeenProducts().map { localToDomainProductMapper.map(it) }
    }
}