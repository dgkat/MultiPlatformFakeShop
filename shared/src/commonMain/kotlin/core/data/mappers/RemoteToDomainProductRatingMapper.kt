package core.data.mappers

import core.data.remote.RemoteProductRating
import core.domain.models.ProductRating

class RemoteToDomainProductRatingMapper {
    fun map(remoteProductRating: RemoteProductRating): ProductRating {
        return ProductRating(
            count = remoteProductRating.count,
            rate = remoteProductRating.rate
        )
    }
}