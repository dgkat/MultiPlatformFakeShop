package home.presentation.mappers

import core.data.remote.RemoteProduct
import core.domain.models.Product
import home.presentation.models.UiHomeProduct

class DomainToUiProductMapper(
    private val ratingMapper: DomainToUiProductRatingMapper
) {
    fun map(product: Product): UiHomeProduct {
        return UiHomeProduct(
            category = product.category,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            rating = ratingMapper.map(product.rating),
            title = product.title,
            productType = product.productType
        )
    }

    fun map(products: List<Product>): List<UiHomeProduct> {
        return products.map { this.map(it) }
    }
}