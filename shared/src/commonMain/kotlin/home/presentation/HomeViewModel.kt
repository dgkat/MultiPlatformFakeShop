package home.presentation

import core.presentation.KMPViewModel
import core.presentation.coroutineScope
import core.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class HomeViewModel(
    val productRepository: ProductRepository
) : KMPViewModel(), KoinComponent {
    private val scope: CoroutineScope = viewModelScope.coroutineScope

    init {
        scope.launch { }
        a.launch {  }
    }
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClicked -> {}
        }
    }
}