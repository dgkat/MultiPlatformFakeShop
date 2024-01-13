package org.dgkat.multiplatform_fake_shop.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.dgkat.multiplatform_fake_shop.productMain.ProductMainScreen
import productMain.presentation.ProductMainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FakeShopRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.PRODUCT_MAIN) {
        composable(
            route = Routes.PRODUCT_MAIN
        ) {
            val productMainViewModel = koinViewModel<ProductMainViewModel>()
            val state by productMainViewModel.state.collectAsStateWithLifecycle()
            ProductMainScreen(state = state, onEvent = productMainViewModel::onEvent)
        }
    }
}