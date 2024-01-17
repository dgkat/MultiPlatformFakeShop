package productMain.presentation

import core.base.KMPViewModel
import core.base.coroutineScope
import core.domain.models.Product
import core.domain.models.Rating
import core.domain.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class  ProductMainViewModel : KMPViewModel(), KoinComponent {
    private val repo: ProductRepository by inject()
    private val coroutineScope = viewModelScope.coroutineScope

    private val _state: MutableStateFlow<ProductMainState> = MutableStateFlow(
        ProductMainState(
            data = Product(
                title = "title val ",
                description = "",
                category = "",
                id = 1,
                image = "",
                price = 0.0,
                rating = Rating(
                    count = 1,
                    rate = 0.0
                )
            ),
            loading = true
        )
    )
    val state = _state.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductMainState(
            data = Product(
                title = "title state in",
                description = "",
                category = "",
                id = 1,
                image = "",
                price = 0.0,
                rating = Rating(
                    count = 1,
                    rate = 0.0
                )
            ),
            loading = true
        )
    )

    init {
        testFun()
    }

    private fun testFun() {
        coroutineScope.launch {
            delay(1000)
            _state.update {
                ProductMainState(
                    data = Product(
                        title = "title 1",
                        description = "",
                        category = "",
                        id = 1,
                        image = "",
                        price = 0.0,
                        rating = Rating(
                            count = 1,
                            rate = 0.0
                        )
                    )
                )
            }
            delay(1000)
            _state.update {
                ProductMainState(
                    data = Product(
                        title = "title 2",
                        description = "",
                        category = "",
                        id = 1,
                        image = "",
                        price = 0.0,
                        rating = Rating(
                            count = 1,
                            rate = 0.0
                        )
                    )
                )
            }
            delay(1000)
            val result = repo.getProductById()
            _state.update {
                ProductMainState(data = Product(
                    title = result.title,
                    description = "",
                    category = "",
                    id = 1,
                    image = "",
                    price = 0.0,
                    rating = Rating(
                        count = 1,
                        rate = 0.0
                    )
                ))
            }
        }
    }

    fun onEvent(event: ProductMainEvent) {
        when (event) {
            is ProductMainEvent.onAskForData -> {}
            ProductMainEvent.onFavoriteClicked -> {}
        }
    }
}