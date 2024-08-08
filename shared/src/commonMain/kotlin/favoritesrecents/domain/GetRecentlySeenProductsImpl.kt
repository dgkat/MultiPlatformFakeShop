package favoritesrecents.domain

class GetRecentlySeenProductsImpl:GetFavoriteRecentProducts {
    override suspend fun invoke(): String {
        return "Recent"
    }
}