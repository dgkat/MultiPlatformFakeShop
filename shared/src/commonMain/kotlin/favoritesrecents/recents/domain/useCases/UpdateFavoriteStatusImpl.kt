package favoritesrecents.recents.domain.useCases

import favoritesrecents.recents.domain.repository.RecentsRepository
import favoritesrecents.sharedDomain.UpdateFavoriteRecentProduct

class UpdateFavoriteStatusImpl(private val recentsRepository: RecentsRepository) : UpdateFavoriteRecentProduct {
    override suspend fun invoke(productId: Int) {
        recentsRepository.updateFavoriteStatus(productId)
    }
}