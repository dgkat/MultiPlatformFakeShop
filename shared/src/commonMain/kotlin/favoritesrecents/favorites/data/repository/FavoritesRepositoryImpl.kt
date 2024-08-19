package favoritesrecents.favorites.data.repository

import core.data.local.ProductsDao
import core.data.mappers.LocalToDomainProductMapper
import core.domain.models.Product
import favoritesrecents.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val productDao: ProductsDao,
    private val localToDomainProductMapper: LocalToDomainProductMapper,
) : FavoritesRepository {
    override fun getFavoriteProducts(): Flow<List<Product>> {
        return productDao.getFavoriteProducts().map { localToDomainProductMapper.map(it) }
    }
}