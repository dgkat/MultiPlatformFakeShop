package core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Upsert
    suspend fun upsertProduct(product: LocalProduct)

    @Delete
    suspend fun deleteProduct(product: LocalProduct)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<LocalProduct>>

    @Query("SELECT id FROM products WHERE isFavorite = 1")
    fun getFavoriteProductIds(): Flow<List<Int>>

    @Query("SELECT id FROM products WHERE isRecentlyViewed = 1")
    fun getRecentlyViewedProductIds(): Flow<List<Int>>

    @Query("SELECT * FROM products WHERE isFavorite = 1")
    fun getFavoriteProducts(): Flow<List<LocalProduct>>

    @Query("SELECT * FROM products WHERE isRecentlyViewed = 1")
    fun getRecentlySeenProducts(): Flow<List<LocalProduct>>
}