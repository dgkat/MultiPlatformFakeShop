package org.dgkat.multiplatform_fake_shop.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import home.presentation.HomeEvent
import home.presentation.HomeRowState
import home.presentation.HomeState
import home.presentation.models.UiHomeProduct

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold { paddingValues ->
        HomeProductColumn(paddingValues, state)
    }
}

@Composable
private fun HomeProductColumn(
    paddingValues: PaddingValues,
    state: HomeState
) {
    val lazyColumnState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyColumnState
    ) {
        items(items = state.data.values.toList(), key = { row -> row.type }) { homeRowState ->
            HomeProductRow(homeRowState)
        }

    }
}

@Composable
fun HomeProductRow(
    rowState: HomeRowState
) {
    val lazyRowState = rememberLazyListState()
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyRowState
    ) {
        items(items = rowState.products, key = { product -> product.id }) { product ->
            HomeProductCard(product)
        }
    }

}

@Composable
fun HomeProductCard(product: UiHomeProduct) {
    Card(
        modifier = Modifier.size(64.dp)
    ) {
        Text(text = product.title)

    }

}