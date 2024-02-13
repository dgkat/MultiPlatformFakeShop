package core.data.repository

import core.data.remote.ProductClient
import core.domain.models.Product
import core.domain.repository.ProductRepository
import core.util.Resource
import org.koin.core.component.KoinComponent

class ProductRepositoryImpl(private val productClient: ProductClient) : KoinComponent,
    ProductRepository {

    override suspend fun getProductById(): Resource<Product> {

        return productClient.getProductById()
    }
}