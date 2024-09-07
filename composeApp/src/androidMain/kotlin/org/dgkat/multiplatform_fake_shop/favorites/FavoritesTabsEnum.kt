package org.dgkat.multiplatform_fake_shop.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.ui.graphics.vector.ImageVector
import org.dgkat.multiplatform_fake_shop.R

enum class FavoritesTabsEnum(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val textId: Int,
    val namedParam: String
) {
    Favorite(
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        textId = R.string.favorites_name,
        namedParam = "favorites"
    ),
    RecentlySeen(
        selectedIcon = Icons.Filled.RemoveRedEye,
        unselectedIcon = Icons.Outlined.RemoveRedEye,
        textId = R.string.recently_seen_name,
        namedParam = "recently_seen"
    )
}