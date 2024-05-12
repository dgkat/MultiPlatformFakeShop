package core.data.remote

import core.data.remote.models.RemoteProduct

interface ProductClient {
    suspend fun getProductById(): RemoteProduct

    suspend fun getProductByType(type: String): List<RemoteProduct>
}
