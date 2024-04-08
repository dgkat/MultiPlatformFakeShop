package org.dgkat.multiplatform_fake_shop.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import org.dgkat.multiplatform_fake_shop.R

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME_SCREEN(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home_name,
        titleTextId = R.string.home_name,
    ),
    SECOND_SCREEN(
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add,
        iconTextId = R.string.second_screen_name,
        titleTextId = R.string.second_screen_name,
    ),
    PROFILE_SCREEN(
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = R.string.profile_name,
        titleTextId = R.string.profile_name,
    ),
}