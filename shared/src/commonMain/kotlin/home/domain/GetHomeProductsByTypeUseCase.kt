package home.domain

import core.data.mappers.RemoteToDomainProductMapper
import core.data.remote.RemoteProduct
import core.domain.models.Product
import core.domain.repository.ProductRepository
import core.domain.util.Resource
import kotlinx.coroutines.delay

class GetHomeProductsByTypeUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(type: String): Resource<List<Product>> {
        //TODO maybe sort
        return productRepository.getProductByType(type)
    }
}

class GetHomeProductsByTypeUseCaseMock(
    private val mapper: RemoteToDomainProductMapper
) {
    suspend operator fun invoke(type: String): Resource<List<Product>> {
        val products = fillData(type)
        val thousandMillis = 1000L
        val delayTime = if (type.length > 10) thousandMillis else type.length * thousandMillis
        delay(delayTime)
        return if (products.isEmpty()) {
            Resource.Error()
        } else {
            Resource.Success(products)
        }
    }

    private fun fillData(type: String): List<Product> {
        val products = mutableListOf<Product>()
        val count = type.length
        repeat(count) {
            products.add(
                mapper.map(
                    (RemoteProduct(
                        category = type,
                        id = count + it,
                        name = type + count + it,
                        price = count.toDouble(),
                        productType = type
                    ))
                )
            )
        }
        return products
    }
}