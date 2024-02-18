package core.domain.repository

import core.domain.models.Product
import core.util.Resource

interface ProductRepository {

    suspend fun getProductById(): Resource<Product>
}