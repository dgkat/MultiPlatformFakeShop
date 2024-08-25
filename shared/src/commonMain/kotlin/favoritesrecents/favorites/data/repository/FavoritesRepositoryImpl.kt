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

    override suspend fun unfavoriteProduct(productId: Int) {
        productDao.unfavoriteProduct(productId)

        /*To check for error eg id not found , find num of rows updated
        * 1. In DAO -> add return type Int to unfavoriteProduct like:
        * @Query("UPDATE products SET isFavorite = 0 WHERE id = :productId")
        * suspend fun unfavoriteProduct(productId: Int) : Int
        * 2. In repoImpl ->  check numOf rowsUpdated > 0 like:
        * val rowsUpdated = productDao.unfavoriteProduct(productId)
        * return rowsUpdated > 0*/

    }
}