package org.dgkat.multiplatform_fake_shop.core.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.dgkat.multiplatform_fake_shop.productMain.ProductMainScreen
import org.dgkat.multiplatform_fake_shop.productMain.ProfileScreen
import org.dgkat.multiplatform_fake_shop.productMain.SecondScreen
import org.koin.androidx.compose.koinViewModel
import productMain.presentation.ProductMainViewModel


const val PROFILE_SCREEN_ROUTE = "profile_screen_route"
const val HOME_SCREEN_ROUTE = "home_screen_route"
const val SECOND_SCREEN_ROUTE = "second_screen_route"

//split them

//home / product

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(HOME_SCREEN_ROUTE, navOptions)
fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_SCREEN_ROUTE
    ) {
        val productMainViewModel = koinViewModel<ProductMainViewModel>()
        val state by productMainViewModel.state.collectAsStateWithLifecycle()
        ProductMainScreen(state = state, onEvent = productMainViewModel::onEvent)
    }
}

//Second screen
fun NavController.navigateToSecondScreen(navOptions: NavOptions) =
    navigate(SECOND_SCREEN_ROUTE, navOptions)

fun NavGraphBuilder.secondScreen() {
    composable(
        route = SECOND_SCREEN_ROUTE,
    ) {
        SecondScreen()
    }
}

//Profile screen
fun NavController.navigateToProfile(navOptions: NavOptions) =
    navigate(PROFILE_SCREEN_ROUTE, navOptions)

fun NavGraphBuilder.profileScreen() {
    composable(
        route = PROFILE_SCREEN_ROUTE,
    ) {
        ProfileScreen()
    }
}