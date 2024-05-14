package org.dgkat.multiplatform_fake_shop.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import home.presentation.HomeEvent
import home.presentation.HomeRowState
import home.presentation.HomeState
import home.presentation.models.UiHomeProduct
import org.dgkat.multiplatform_fake_shop.core.composables.endReached

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold { paddingValues ->
        HomeProductColumn(paddingValues, state, onEvent)
    }
}

@Composable
private fun HomeProductColumn(
    paddingValues: PaddingValues,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val lazyColumnState = rememberLazyListState()

    val bottomReached by remember { derivedStateOf { lazyColumnState.endReached() } }

    LaunchedEffect(bottomReached) {
        if (bottomReached) onEvent(HomeEvent.OnColumnEndReached)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyColumnState
    ) {
        items(items = state.data) { homeRowStateFlow ->
            val homeRowState by homeRowStateFlow.collectAsStateWithLifecycle()
            HomeProductRow(homeRowState, onEvent)
        }
    }
}

@Composable
fun HomeProductRow(
    rowState: HomeRowState,
    onEvent: (HomeEvent) -> Unit
) {
    val lazyRowState = rememberLazyListState()
    val endReached by remember { derivedStateOf { lazyRowState.endReached() } }

    LaunchedEffect(endReached) {
        if (endReached) onEvent(HomeEvent.OnRowEndReached(rowState.type))
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyRowState
    ) {
        items(items = rowState.products/*, key = { product -> product.id }*/) { product ->
            HomeProductCard(product)
        }
    }

}

@Composable
fun HomeProductCard(product: UiHomeProduct) {
    Card(
        shape = RoundedCornerShape(size = 16.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.size(width = 120.dp, height = 240.dp)
        ) {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = product.title,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = "${product.productType} ${product.price}",
                fontSize = 32.sp
            )
        }
    }
}