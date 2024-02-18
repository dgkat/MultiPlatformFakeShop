package core.data.remote

import core.domain.models.Product
import core.util.Resource

interface ProductClient {
    suspend fun getProductById(): Resource<Product>
}
