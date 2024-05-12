package core.data.local.mappers

import core.data.local.models.LocalProductRating
import core.domain.models.ProductRating

class LocalToDomainProductRatingMapper {
    fun map(localProductRating: LocalProductRating): ProductRating {
        return ProductRating(
            count = localProductRating.count,
            rate = localProductRating.rate
        )
    }
}