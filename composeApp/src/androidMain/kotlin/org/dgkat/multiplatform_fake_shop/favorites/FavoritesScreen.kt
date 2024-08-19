package org.dgkat.multiplatform_fake_shop.favorites


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import favoritesrecents.sharedPresentation.FavoritesRecentsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    // state: FavoritesRecentsState,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { FavoritesTabsEnum.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            FavoritesTabsEnum.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = { Text(text = stringResource(id = currentTab.textId)) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTabIndex.value == index)
                                currentTab.selectedIcon else currentTab.unselectedIcon,
                            contentDescription = "Tab Icon"
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> {
                   FavoritesScreenTab()
                }

                1 -> {
                    RecentsScreenTab()
                }
            }
        }
    }
}

@Composable
fun FavoritesScreenTab(
    viewModel: FavoritesRecentsViewModel = koinViewModel(named("favorites"))
) {
    val state by viewModel.favoritesRecentsState.collectAsStateWithLifecycle()
    FavoritesRecentsTab(products = state.products,loading = state.loading)
}

@Composable
fun RecentsScreenTab(
    viewModel: FavoritesRecentsViewModel = koinViewModel(named("recently_seen"))
) {
    val state by viewModel.favoritesRecentsState.collectAsStateWithLifecycle()
    FavoritesRecentsTab(products = state.products,loading = state.loading)
}
