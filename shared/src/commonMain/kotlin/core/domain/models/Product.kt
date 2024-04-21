package core.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Product (
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: ProductRating,
    val title: String,
    val productType : String
)

@Serializable
data class ProductRating(
    val count: Int,
    val rate: Double
)