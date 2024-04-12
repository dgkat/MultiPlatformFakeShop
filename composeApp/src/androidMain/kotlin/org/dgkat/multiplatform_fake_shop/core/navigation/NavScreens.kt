package org.dgkat.multiplatform_fake_shop.core.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import home.presentation.HomeViewModel
import org.dgkat.multiplatform_fake_shop.home.HomeScreen
import org.dgkat.multiplatform_fake_shop.productMain.ProfileScreen
import org.dgkat.multiplatform_fake_shop.productMain.SecondScreen
import org.koin.androidx.compose.koinViewModel
import productMain.presentation.ProductMainViewModel
import java.net.URLDecoder
import java.net.URLEncoder


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
        val homeViewModel = koinViewModel<HomeViewModel>()
        val state by homeViewModel.state.collectAsStateWithLifecycle()
        HomeScreen(state = state, onEvent = homeViewModel::onEvent)
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

//Screen with arg
private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val TOPIC_ID_ARG = "topicId"

internal class TopicArgs(val topicId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(URLDecoder.decode(checkNotNull(savedStateHandle[TOPIC_ID_ARG]), URL_CHARACTER_ENCODING))
}
fun NavController.navigateToTopic(topicId: String) {
    val encodedId = URLEncoder.encode(topicId, URL_CHARACTER_ENCODING)
    navigate("topic_route/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.topicScreen(
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable(
        route = "topic_route/{$TOPIC_ID_ARG}",
        arguments = listOf(
            navArgument(TOPIC_ID_ARG) { type = NavType.StringType },
        ),
    ) {
       // TopicRoute(onBackClick = onBackClick, onTopicClick = onTopicClick)
    }
}