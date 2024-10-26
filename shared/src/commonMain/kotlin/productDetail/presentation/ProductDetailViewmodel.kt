package productDetail.presentation

import core.presentation.KMPViewModel
import core.presentation.coroutineScope
import home.domain.repository.HomeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class  ProductMainViewModel : KMPViewModel(), KoinComponent {

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            ProductDetailEvent.OnFavoriteClicked -> {}
        }
    }
}