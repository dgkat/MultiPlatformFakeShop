package home.domain.repository

import core.domain.models.Product
import core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    //Remote
    suspend fun getProductById(): Resource<Product>

    suspend fun getProductsByType(type:String): Resource<List<Product>>

    //Local
    suspend fun saveProductToDB(product: Product)

    fun getFavoriteProductIdsFromDb(): Flow<List<Int>>
}