package home.domain.useCases

import kotlinx.coroutines.flow.Flow

interface GetFavoriteProductIds {
    operator fun invoke(): Flow<List<Int>>
}