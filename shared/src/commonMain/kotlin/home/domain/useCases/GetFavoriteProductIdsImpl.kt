package home.domain.useCases

import home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteProductIdsImpl(
    private val homeRepository: HomeRepository
) : GetFavoriteProductIds {
    override fun invoke(): Flow<List<Int>> {
        return homeRepository.getFavoriteProductIdsFromDb()
    }
}