package home.presentation.models

import kotlinx.serialization.Serializable


@Serializable
data class UiHomeProduct (
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: UiHomeProductRating,
    val title: String,
    val productType : String,
    val isFavorite: Boolean = false,
    val isRecentlyViewed: Boolean = false
)

@Serializable
data class UiHomeProductRating(
    val count: Int,
    val rate: Double
)