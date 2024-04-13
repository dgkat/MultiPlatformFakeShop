package core.data.mappers

import core.data.remote.RemoteProduct
import core.domain.models.Product

class RemoteToDomainProductMapper(
    private val typeMapper: RemoteToDomainProductTypeMapper,
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
            productType = typeMapper.map(remoteProduct.productType)
        )
    }

    fun map(remoteProducts: List<RemoteProduct>): List<Product> {
        return remoteProducts.map { this.map(it) }
    }
}