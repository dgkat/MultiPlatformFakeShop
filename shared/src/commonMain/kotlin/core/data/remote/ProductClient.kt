package core.data.remote

interface ProductClient {
    suspend fun getProductById(): RemoteProduct

    suspend fun getProductByType(type: String): List<RemoteProduct>
}
