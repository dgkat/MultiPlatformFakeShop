package home.domain

import home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteProductIdsImpl(
    private val homeRepository: HomeRepository
) : GetFavoriteProductIds {
    override fun invoke(): Flow<List<Int>> {
        return homeRepository.getFavoriteProductIdsFromDb()
    }
}