package home.presentation.mappers

import core.domain.models.Product
import core.domain.models.ProductRating
import home.presentation.models.UiHomeProduct
import home.presentation.models.UiHomeProductRating

class UiToDomainProductMapper(
    private val ratingMapper: UiToDomainProductRatingMapper
) {
    fun map(product: UiHomeProduct): Product {
        return Product(
            category = product.category,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            rating = ratingMapper.map(product.rating),
            title = product.title,
            productType = product.productType,
            isFavorite = product.isFavorite,
            isRecentlyViewed = product.isRecentlyViewed
        )
    }

    fun map(products: List<UiHomeProduct>): List<Product> {
        return products.map { this.map(it) }
    }
}

class UiToDomainProductRatingMapper {
    fun map(productRating: UiHomeProductRating): ProductRating {
        return ProductRating(
            count = productRating.count,
            rate = productRating.rate
        )
    }
}