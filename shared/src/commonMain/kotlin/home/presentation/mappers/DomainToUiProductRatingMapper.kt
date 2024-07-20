package home.presentation.mappers

import core.domain.models.ProductRating
import home.presentation.models.UiHomeProductRating


class DomainToUiProductRatingMapper {
    fun map(productRating: ProductRating): UiHomeProductRating {
        return UiHomeProductRating(
            count = productRating.count,
            rate = productRating.rate
        )
    }
}