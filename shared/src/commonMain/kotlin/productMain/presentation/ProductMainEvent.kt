package productMain.presentation

sealed class ProductMainEvent {
    object onFavoriteClicked : ProductMainEvent()
    data class onAskForData(val id: String) : ProductMainEvent()
}