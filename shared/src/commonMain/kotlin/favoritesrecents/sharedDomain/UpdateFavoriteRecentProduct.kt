package favoritesrecents.sharedDomain

interface UpdateFavoriteRecentProduct {
    suspend operator fun invoke(productId: Int)
}