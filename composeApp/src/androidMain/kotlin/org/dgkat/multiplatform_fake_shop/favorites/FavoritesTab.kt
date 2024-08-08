package org.dgkat.multiplatform_fake_shop.favorites

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoritesTab(modifier: Modifier = Modifier, text: String) {
    Surface {
        Text(text = text)
    }
}