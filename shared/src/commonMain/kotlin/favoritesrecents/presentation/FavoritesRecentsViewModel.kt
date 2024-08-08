package favoritesrecents.presentation

import core.presentation.KMPViewModel
import favoritesrecents.domain.GetFavoriteRecentProducts
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FavoritesRecentsViewModel(
    private val getFavoriteRecentProducts: GetFavoriteRecentProducts
) : KMPViewModel(), KoinComponent {
    private val _favoritesRecentsState: MutableStateFlow<FavoritesRecentsState> = MutableStateFlow(
        FavoritesRecentsState()
    )
    val favoritesRecentsState = _favoritesRecentsState.stateIn(
        scope = a,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FavoritesRecentsState()
    )

    init {

        a.launch {
            delay(1000)
            _favoritesRecentsState.update { state ->
                state.copy(
                    testSTR = getFavoriteRecentProducts()
                )
            }
        }
    }

}