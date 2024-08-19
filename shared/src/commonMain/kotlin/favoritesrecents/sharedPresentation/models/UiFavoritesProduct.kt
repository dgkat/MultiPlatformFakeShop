package favoritesrecents.sharedPresentation.models

import kotlinx.serialization.Serializable

@Serializable
data class UiFavoritesProduct (
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: UiFavoritesProductRating,
    val title: String,
    val productType : String,
    val isFavorite: Boolean = false,
    val isRecentlyViewed: Boolean = false
)

@Serializable
data class UiFavoritesProductRating(
    val count: Int,
    val rate: Double
)