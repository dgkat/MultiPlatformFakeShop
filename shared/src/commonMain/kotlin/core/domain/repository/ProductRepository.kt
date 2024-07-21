package core.domain.repository

import core.domain.models.Product
import core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    //Remote
    suspend fun getProductById(): Resource<Product>

    suspend fun getProductByType(type:String): Resource<List<Product>>

    //Local
    suspend fun addProductToDB(product: Product)

    suspend fun getFavoriteProductIdsFromDb(): Flow<List<Int>>
}