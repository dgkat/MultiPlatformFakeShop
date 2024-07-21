package core.data.mappers

import core.data.local.LocalProduct
import core.data.local.LocalProductRating
import core.domain.models.Product
import core.domain.models.ProductRating

class DomainToLocalProductMapper(
    private val ratingMapper: DomainToLocalProductRatingMapper
) {
    fun map(product: Product): LocalProduct {
        return LocalProduct(
            category = product.category,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            rating = ratingMapper.map(product.rating),
            name = product.title,
            productType = product.productType,
            isFavorite = product.isFavorite,
            isRecentlyViewed = product.isRecentlyViewed
        )
    }

    fun map(localProducts: List<Product>): List<LocalProduct> {
        return localProducts.map { this.map(it) }
    }
}

class DomainToLocalProductRatingMapper {
    fun map(productRating: ProductRating): LocalProductRating {
        return LocalProductRating(
            count = productRating.count,
            rate = productRating.rate
        )
    }
}