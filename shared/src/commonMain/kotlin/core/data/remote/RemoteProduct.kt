package core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RemoteProduct(
    val category: String = "",
    val description: String = "",
    val id: Int,
    val image: String = "",
    val price: Double,
    val rating: RemoteProductRating = RemoteProductRating(1, 1.0),
    val name: String = "",
    val productType: String = ""
)

@Serializable
data class RemoteProductRating(
    val count: Int,
    val rate: Double
)