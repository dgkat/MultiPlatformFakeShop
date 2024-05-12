package core.data.remote.mappers

import core.data.remote.models.RemoteProductRating
import core.domain.models.ProductRating

class RemoteToDomainProductRatingMapper {
    fun map(remoteProductRating: RemoteProductRating): ProductRating {
        return ProductRating(
            count = remoteProductRating.count,
            rate = remoteProductRating.rate
        )
    }
}