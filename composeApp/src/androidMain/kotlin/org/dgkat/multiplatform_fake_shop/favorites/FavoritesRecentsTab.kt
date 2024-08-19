package org.dgkat.multiplatform_fake_shop.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import core.domain.models.Product
import core.domain.models.ProductRating
import favoritesrecents.sharedPresentation.FavoritesRecentsEvent
import home.presentation.HomeEvent
import org.dgkat.multiplatform_fake_shop.home.FavoriteButton
import org.dgkat.multiplatform_fake_shop.home.RatingRow

@Composable
fun FavoritesRecentsTab(modifier: Modifier = Modifier, products: List<Product>, loading: Boolean) {
    Surface {

    }
}

@Composable
fun FavoritesRecentsProductRow(
    modifier: Modifier = Modifier,
    product: Product,
    onEvent: (FavoritesRecentsEvent) -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.clickable {
            onEvent(FavoritesRecentsEvent.OnProductClicked(product.id))
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Box(modifier = Modifier.padding(all = 4.dp).weight(1f)) {
                AsyncImage(
                    model = "https://i.postimg.cc/LsXBmP7n/test-laptop-1.jpg",
                    contentDescription = "coil_image",
                    imageLoader = ImageLoader(context = LocalContext.current),
                    modifier = Modifier

                        .clip(shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .aspectRatio(150f / 150f)
                )
                FavoriteButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    isFavorite = product.isFavorite,
                    onClick = { onEvent(FavoritesRecentsEvent.OnFavoriteClicked(product.id)) }
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier.padding(all = 8.dp),
                    text = product.title,
                    fontSize = 14.sp
                )
                RatingRow(ratingCount = product.rating.count, ratingRate = product.rating.rate)
                Text(
                    modifier = Modifier.padding(all = 8.dp),
                    text = "Price: " + product.price.toString() + " $",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeProductCardPreview() {
    val rating = ProductRating(count = 5, rate = 3.4)
    val product = Product(
        category = "category",
        description = "desc",
        id = 1,
        image = "image",
        price = 15.5,
        rating = rating,
        title = "title",
        productType = "type"
    )
    FavoritesRecentsProductRow(product = product, onEvent = { HomeEvent.OnProductClicked(1) })
}