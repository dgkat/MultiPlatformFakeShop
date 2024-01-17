package core.domain.models

@kotlinx.serialization.Serializable
data class Rating(
    val count: Int,
    val rate: Double
)