package core.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class LocalProduct(
    val category: String = "",
    val description: String = "",
    @PrimaryKey val id: Int,
    val image: String = "",
    val price: Double,
    @Embedded val rating: LocalProductRating,
    val name: String = "",
    val productType: String = "",
    val isFavorite: Boolean = false,
    val isRecentlyViewed: Boolean = false
)

data class LocalProductRating(
    val rate: Double,
    val count: Int
)