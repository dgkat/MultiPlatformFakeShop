package core.data.remote

import core.domain.models.ProductType
import core.util.Resource

interface ProductClient {
    suspend fun getProductById(): RemoteProduct

    suspend fun getProductByType(type: String): List<RemoteProduct>
}
