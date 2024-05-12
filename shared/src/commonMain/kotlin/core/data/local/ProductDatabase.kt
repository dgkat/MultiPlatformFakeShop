package core.data.local

import core.data.local.models.LocalProduct

interface ProductDatabase {
    suspend fun saveProduct(product: LocalProduct)
    suspend fun deleteProductById(id: Int)
    suspend fun getProductById(id: Int): LocalProduct
    suspend fun getAllFavorites(): List<LocalProduct>
}