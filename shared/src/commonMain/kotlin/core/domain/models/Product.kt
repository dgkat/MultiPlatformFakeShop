package core.domain.models

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: ProductRating,
    val title: String,
    val productType: String
)

data class ProductRating(
    val count: Int,
    val rate: Double
)