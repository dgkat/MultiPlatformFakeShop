package favoritesrecents.domain

class GetFavoriteProductsImpl:GetFavoriteRecentProducts {
    override suspend fun invoke(): String {
        return "Favorite Products"
    }
}