package core.data.mappers

import core.data.remote.RemoteProduct
import core.data.remote.RemoteProductRating
import core.domain.models.Product
import core.domain.models.ProductRating
import home.presentation.mappers.DomainToUiProductRatingMapper

class RemoteToDomainProductMapper(
    private val ratingMapper: RemoteToDomainProductRatingMapper
) {
    fun map(remoteProduct: RemoteProduct): Product {
        return Product(
            category = remoteProduct.category,
            description = remoteProduct.description,
            id = remoteProduct.id,
            image = remoteProduct.image,
            price = remoteProduct.price,
            rating = ratingMapper.map(remoteProduct.rating),
            title = remoteProduct.name,
            productType = remoteProduct.productType
        )
    }

    fun map(remoteProducts: List<RemoteProduct>): List<Product> {
        return remoteProducts.map { this.map(it) }
    }
}

class RemoteToDomainProductRatingMapper {
    fun map(remoteProductRating: RemoteProductRating): ProductRating {
        return ProductRating(
            count = remoteProductRating.count,
            rate = remoteProductRating.rate
        )
    }
}