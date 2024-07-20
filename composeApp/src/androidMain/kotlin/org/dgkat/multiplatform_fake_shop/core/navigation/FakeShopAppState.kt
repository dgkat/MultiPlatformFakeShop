package org.dgkat.multiplatform_fake_shop.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

@Composable
fun rememberFakeShopAppState(
    navController: NavHostController = rememberNavController(),
): FakeShopAppState {
   // NavigationTrackingSideEffect(navController)
    return remember(
        navController
    ) {
        FakeShopAppState(
            navController
        )
    }
}

@Stable
class FakeShopAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME_SCREEN_ROUTE -> TopLevelDestination.HOME_SCREEN
            SECOND_SCREEN_ROUTE -> TopLevelDestination.SECOND_SCREEN
            PROFILE_SCREEN_ROUTE -> TopLevelDestination.PROFILE_SCREEN
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = true


    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {

            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
                anim {
                    enter = android.R.animator.fade_in
                }
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME_SCREEN -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.SECOND_SCREEN -> navController.navigateToSecondScreen(topLevelNavOptions)
                TopLevelDestination.PROFILE_SCREEN -> navController.navigateToProfile(topLevelNavOptions)
            }

    }
    // add searchs
    //fun navigateToSearch() = navController.navigateToSearch()
}
