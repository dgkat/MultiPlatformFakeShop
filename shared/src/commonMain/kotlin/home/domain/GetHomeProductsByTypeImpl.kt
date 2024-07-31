package home.domain

import core.data.mappers.RemoteToDomainProductMapper
import core.data.remote.RemoteProduct
import core.domain.models.Product
import home.domain.repository.HomeRepository
import core.domain.util.Resource
import kotlinx.coroutines.delay

class GetHomeProductsByTypeImpl(
    private val homeRepository: HomeRepository
) : GetHomeProductsByType {
    override suspend fun invoke(type: String, pageFrom: Int): Resource<List<Product>> {
        return homeRepository.getProductsByType(type)
    }
}

class GetHomeProductsByTypeMock(
    private val mapper: RemoteToDomainProductMapper
) : GetHomeProductsByType {
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

    override suspend operator fun invoke(type: String, pageFrom: Int): Resource<List<Product>> {
        val thousandMillis = 1000L
        val delayTime = if (type.length > 10) thousandMillis else type.length * thousandMillis
        delay(delayTime)
        val products = getData(type)
        return if (products.isEmpty()) {
            Resource.Error()
        } else if (products.size < 5) {
            Resource.Success(data = products, code = 204)
        } else {
            Resource.Success(products)
        }
    }

    private fun getData(type: String): List<Product> {
        val products = map[type] ?: return emptyList()
        /*val to = if (pageFrom + 5 > products.size) products.size else pageFrom + 5
        return products.subList(pageFrom, to)*/

        return products.subList(0, 4)
    }

    private fun fillData(type: String): List<Product> {
        val products = mutableListOf<Product>()
        val count = type.length
        repeat(count) {
            products.add(
                mapper.map(
                    (RemoteProduct(
                        category = type,
                        id = (count.toString() + it.toString()).toInt(),
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