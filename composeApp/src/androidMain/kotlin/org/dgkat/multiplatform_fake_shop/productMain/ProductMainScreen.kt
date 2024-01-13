package org.dgkat.multiplatform_fake_shop.productMain

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import productMain.presentation.ProductMainEvent
import productMain.presentation.ProductMainState

@Composable
fun ProductMainScreen(
    state: ProductMainState,
    onEvent: (ProductMainEvent) -> Unit
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TitleText(title = state.data?.title ?: "empty")
            }
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(text = title)
}