package core.domain.repository

import core.domain.models.Product

interface ProductRepository {

    suspend fun getProductById(): Product
}