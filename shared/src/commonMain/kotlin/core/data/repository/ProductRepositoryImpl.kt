package core.data.repository

import core.data.remote.mappers.RemoteToDomainProductMapper
import core.data.remote.ProductClient
import core.domain.models.Product
import core.domain.repository.ProductRepository
import core.domain.util.Resource
import core.data.util.safeRequest
import org.koin.core.component.KoinComponent

class ProductRepositoryImpl(
    private val productClient: ProductClient,
    private val productMapper: RemoteToDomainProductMapper
) : KoinComponent,
    ProductRepository {

    override suspend fun getProductById(): Resource<Product> {
        val response = productClient.getProductById()
        return safeRequest { productMapper.map(response) }
    }

    override suspend fun getProductByType(type: String): Resource<List<Product>> {
        return safeRequest {
            val response = productClient.getProductByType(type)
            productMapper.map(response)
        }
    }
}