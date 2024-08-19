package favoritesrecents.sharedPresentation

import core.domain.models.Product

data class FavoritesRecentsState(
    val loading: Boolean = true,
    val products: List<Product> = emptyList(),
)