package org.dgkat.multiplatform_fake_shop.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import home.presentation.HomeEvent
import home.presentation.HomeRowState
import home.presentation.HomeState
import home.presentation.models.UiHomeProduct
import home.presentation.models.UiHomeProductRating
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
        items(items = state.homeRowStates, key = { it.type }) { homeRowState ->
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
    if (!rowState.allDataLoaded) {
        val endReached by remember { derivedStateOf { lazyRowState.endReached() } }

        LaunchedEffect(endReached) {
            if (endReached) onEvent(HomeEvent.OnRowEndReached(rowState.type))
        }
    }
    Column {
        Text(
            modifier = Modifier.padding(all = 8.dp),
            text = rowState.type,
            fontSize = 18.sp
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyRowState,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(items = rowState.products/*, key = { product -> product.id }*/) { product ->
                HomeProductCard(product)
            }
        }
    }
}

@Composable
fun HomeProductCard(product: UiHomeProduct) {
    Card(
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.size(width = 180.dp, height = 320.dp)
        ) {
            Box(modifier = Modifier.padding(all = 4.dp)) {
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
                    isFavorite = false,
                    onClick = {}
                )
            }
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = product.title,
                fontSize = 14.sp
            )
            RatingRow(productRating = product.rating)
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = "Price: " + product.price.toString() + " $",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun FavoriteButton(modifier: Modifier = Modifier, isFavorite: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .clickable { onClick() },
    ) {
        AnimatedContent(
            targetState = isFavorite,
            label = ""
        ) { favorite ->
            Icon(
                tint = if (favorite) {
                    Color.Red
                } else {
                    Color.Gray
                },
                modifier = Modifier,
                imageVector = if (favorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null
            )
        }
    }
}

@Composable
fun RatingRow(modifier: Modifier = Modifier, productRating: UiHomeProductRating) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StarRatingBar(rating = productRating.rate, modifier = Modifier.padding(start = 8.dp))
        Text(
            modifier = modifier,
            text = "${productRating.rate} (${productRating.count})",
            fontSize = 12.sp
        )
    }
}

@Composable
fun StarRatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    filledImage: ImageVector = Icons.Rounded.Star,
    emptyImage: ImageVector = Icons.Rounded.StarOutline,
    filledColor: Color = Color.Yellow,
    emptyColor: Color = Color.Gray
) {
    Row(modifier = modifier) {
        val filledStars = rating.toInt()
        val partialStarPercentage = ((rating - filledStars) * 100).toFloat()
        val emptyStars = 5 - filledStars - if (partialStarPercentage > 0) 1 else 0

        // Draw filled stars
        repeat(filledStars) {
            StarImage(
                image = filledImage,
                color = filledColor,
                imageSize = 16.dp
            )
        }

        // Draw partially filled star
        if (partialStarPercentage > 0) {
            PartialStarImage(
                fillPercentage = partialStarPercentage,
                filledImage = filledImage,
                emptyImage = emptyImage,
                filledColor = filledColor,
                emptyColor = emptyColor,
                imageSize = 16.dp
            )
        }

        // Draw empty stars
        repeat(emptyStars) {
            StarImage(
                image = emptyImage,
                color = emptyColor,
                imageSize = 16.dp
            )
        }
    }
}

@Composable
fun StarImage(
    image: ImageVector,
    modifier: Modifier = Modifier,
    color: Color,
    imageSize: Dp = 24.dp
) {
    Icon(
        imageVector = image,
        contentDescription = null,
        modifier = modifier.size(imageSize),
        tint = color
    )
}

@Composable
fun PartialStarImage(
    modifier: Modifier = Modifier,
    filledImage: ImageVector = Icons.Rounded.Star,
    emptyImage: ImageVector = Icons.Rounded.StarOutline,
    filledColor: Color = Color.Yellow,
    emptyColor: Color = Color.Gray,
    fillPercentage: Float,
    imageSize: Dp = 24.dp
) {
    Box(modifier = modifier) {
        //Empty part
        Icon(
            imageVector = emptyImage,
            contentDescription = null,
            modifier = modifier
                .size(imageSize)
                .drawWithContent {
                    clipRect(left = size.width * (fillPercentage / 100)) {
                        this@drawWithContent.drawContent()
                    }
                },
            tint = emptyColor
        )
        //Filled part
        Icon(
            imageVector = filledImage,
            contentDescription = null,
            modifier = modifier
                .size(imageSize)
                .drawWithContent {
                    clipRect(right = size.width * (fillPercentage / 100)) {
                        this@drawWithContent.drawContent()
                    }
                },
            tint = filledColor
        )
    }
}

@Preview
@Composable
private fun HomeProductCardPreview() {
    val rating = UiHomeProductRating(count = 5, rate = 3.4)
    val product = UiHomeProduct(
        category = "category",
        description = "desc",
        id = 1,
        image = "image",
        price = 15.5,
        rating = rating,
        title = "title",
        productType = "type"
    )
    HomeProductCard(product)
}