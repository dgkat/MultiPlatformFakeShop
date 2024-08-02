package core.data.mappers

import core.data.local.LocalProduct
import core.data.local.LocalProductRating
import core.domain.models.Product
import core.domain.models.ProductRating

class LocalToDomainProductMapper(
    private val ratingMapper: LocalToDomainProductRatingMapper
) {
    fun map(localProduct: LocalProduct): Product {
        return Product(
            category = localProduct.category,
            description = localProduct.description,
            id = localProduct.id,
            image = localProduct.image,
            price = localProduct.price,
            rating = ratingMapper.map(localProduct.rating),
            title = localProduct.name,
            productType = localProduct.productType,
            isFavorite = localProduct.isFavorite,
            isRecentlyViewed = localProduct.isRecentlyViewed
        )
    }

    fun map(localProducts: List<LocalProduct>): List<Product> {
        return localProducts.map { this.map(it) }
    }
}

class LocalToDomainProductRatingMapper {
    fun map(localProductRating: LocalProductRating): ProductRating {
        return ProductRating(
            count = localProductRating.count,
            rate = localProductRating.rate
        )
    }
}