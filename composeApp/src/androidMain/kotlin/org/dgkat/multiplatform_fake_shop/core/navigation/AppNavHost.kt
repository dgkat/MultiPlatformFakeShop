package org.dgkat.multiplatform_fake_shop.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun FakeShopNavHost(
    appState: FakeShopAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_SCREEN_ROUTE ,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        //WIP add animation here?
        homeScreen()
        secondScreen()
        profileScreen()
    }
}