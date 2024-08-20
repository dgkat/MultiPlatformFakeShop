package favoritesrecents.sharedPresentation

import core.presentation.KMPViewModel
import favoritesrecents.sharedDomain.GetFavoriteRecentProducts
import favoritesrecents.sharedDomain.UpdateFavoriteRecentProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FavoritesRecentsViewModel(
    private val getFavoriteRecentProducts: GetFavoriteRecentProducts,
    private val updateFavoriteRecentProduct: UpdateFavoriteRecentProduct
) : KMPViewModel(), KoinComponent {
    private val _favoritesRecentsState: MutableStateFlow<FavoritesRecentsState> = MutableStateFlow(
        FavoritesRecentsState()
    )
    val favoritesRecentsState = _favoritesRecentsState.stateIn(
        scope = a,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FavoritesRecentsState()
    )
    //TODO Check if below is different to above, public state for UI
    //val favoritesRecentsState: StateFlow<FavoritesRecentsState> = _favoritesRecentsState

    init {
        observeFavoriteRecentProducts()
    }

    private fun observeFavoriteRecentProducts() {
        a.launch {
            getFavoriteRecentProducts()
                .catch {
                    //TODO handle error
                    _favoritesRecentsState.update {
                        it.copy(
                            loading = false,
                            products = emptyList()
                        )
                    }
                }
                .collect { products ->
                    _favoritesRecentsState.update {
                        it.copy(
                            loading = false,
                            products = products
                        )
                    }
                }
        }
    }

    fun onEvent(event: FavoritesRecentsEvent) {
        when (event) {
            is FavoritesRecentsEvent.OnFavoriteClicked -> onFavoriteClicked(event.productId)
            is FavoritesRecentsEvent.OnProductClicked -> TODO()
        }
    }

    private fun onFavoriteClicked(productId: Int) {
        a.launch {
            updateFavoriteRecentProduct(productId)
        }
    }
}