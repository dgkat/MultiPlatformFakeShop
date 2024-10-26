package productDetail.presentation

sealed class ProductDetailEvent {
    data object OnFavoriteClicked : ProductDetailEvent()
}