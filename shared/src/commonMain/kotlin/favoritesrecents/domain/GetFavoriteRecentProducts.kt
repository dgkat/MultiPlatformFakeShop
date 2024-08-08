package favoritesrecents.domain

interface GetFavoriteRecentProducts {
    suspend operator fun invoke(): String
}