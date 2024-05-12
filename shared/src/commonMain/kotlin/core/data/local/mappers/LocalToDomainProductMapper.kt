package core.data.local.mappers

import core.data.local.models.LocalProduct
import core.domain.models.Product
import core.domain.models.ProductRating

class LocalToDomainProductMapper(
    private val ratingMapper: LocalToDomainProductRatingMapper
) {
    fun map(localProduct: LocalProduct): Product {
        return Product(
            category = localProduct.category,
            description = localProduct.description,
            id = localProduct._id,
            image = localProduct.image,
            price = localProduct.price,
            rating = localProduct.rating?.let { ratingMapper.map(it) } ?: ProductRating(0, 0.0),
            title = localProduct.name,
            productType = localProduct.productType
        )
    }

    fun map(localProduct: List<LocalProduct>): List<Product> {
        return localProduct.map { this.map(it) }
    }
}