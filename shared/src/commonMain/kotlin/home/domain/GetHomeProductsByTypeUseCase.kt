package home.domain

import core.data.mappers.RemoteToDomainProductMapper
import core.data.remote.RemoteProduct
import core.domain.models.Product
import core.domain.repository.ProductRepository
import core.domain.util.RemoteError
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
    val types = listOf(
        "phone",
        "laptop",
        "asdf",
        "",
        "this_is_a_long_text_to_represent_many_items_in_a_row"
    )

    val map = mutableMapOf<String, List<Product>>()
    init {
        types.forEach {
            map[it] = fillData(it)
        }
    }

    suspend operator fun invoke(type: String, pageFrom: Int): Resource<List<Product>> {
        val thousandMillis = 1000L
        val delayTime = if (type.length > 10) thousandMillis else type.length * thousandMillis
        delay(delayTime)
        val products = getData(type, pageFrom)
        return if (products.isEmpty()) {
            Resource.Error()
        } else if (products.size < 5) {
            Resource.Success(data = products, code = 204)
        } else {
            Resource.Success(products)
        }
    }

    private fun getData(type: String,pageFrom: Int): List<Product> {
        val products = map[type] ?: emptyList()
        val to = if (pageFrom + 5 > products.size) products.size else pageFrom + 5
        return products.subList(pageFrom, to)
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