package home.presentation

import core.domain.util.Resource
import core.presentation.KMPViewModel
import home.domain.GetFavoriteProductIds
import home.domain.GetHomeProductsByTypeUseCaseMock
import home.domain.SaveProduct
import home.presentation.mappers.DomainToUiProductMapper
import home.presentation.mappers.UiToDomainProductMapper
import home.presentation.models.UiHomeProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeViewModel(
    private val domainToUiProductMapper: DomainToUiProductMapper,
    private val uiToDomainProductMapper: UiToDomainProductMapper,
    private val saveProduct: SaveProduct
) : KMPViewModel(), KoinComponent {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState()
    )
    val state = _state.stateIn(
        scope = a,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    private val homeRowVMSlices = mutableListOf<HomeRowVMSlice>()

    init {
        //check if internet connection to show error / call for types to show

        val categories = listOf(
            "phone",
            "laptop",
            "asdf",
            "this_is_a_long_text_to_represent_many_items_in_a_row"
        )


        // create a class that handles this (factory maybe?)( we can then inject the handler there)
        //TODO gets bellow should be single for the lifecycle of the VM,
        // inject in VM or scope to vm with koin
        categories.forEachIndexed { index, type ->
            val slice = HomeRowVMSlice(
                getHomeProductsByTypeUseCase = get(),
                domainToUiProductMapper = domainToUiProductMapper,
                getFavoriteProductIds = get(),
                type = type,
                parentScope = a
            )
            homeRowVMSlices.add(slice)
            addRowState(HomeRowState(type = type), index)
            a.launch {
                slice.invoke()
                slice.state.collect { homeRowState ->
                    updateRowState(homeRowState, index)
                }
            }
        }
    }

    private fun updateRowState(newRowState: HomeRowState, rowIndex: Int) {
        _state.update { homeState ->
            val updatedHomeRowStates = homeState.homeRowStates.toMutableList().apply {
                this[rowIndex] = newRowState
            }
            homeState.copy(homeRowStates = updatedHomeRowStates)
        }
    }

    private fun addRowState(newRowState: HomeRowState, rowIndex: Int) {
        _state.update { homeState ->
            val updatedHomeRowStates = homeState.homeRowStates.toMutableList().apply {
                add(rowIndex, newRowState)
            }
            homeState.copy(homeRowStates = updatedHomeRowStates)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClicked -> {

            }

            HomeEvent.OnColumnEndReached -> {
                println("column bottom reached")
            }

            is HomeEvent.OnRowEndReached -> {
                println("row end reached ${event.type}")
                /*a.launch {
                    homeRowVMSlices.find { it.state.value.type == event.type }?.invoke()
                }*/
            }

            is HomeEvent.OnFavoriteClicked -> {
                val favoriteProduct = event.product.copy(isFavorite = !event.product.isFavorite)
                a.launch {
                    saveProduct.saveProduct(uiToDomainProductMapper.map(favoriteProduct))
                }
            }
        }
    }
}


data class HomeRowState(
    val type: String = "",
    val products: List<UiHomeProduct> = emptyList(),
    val loading: Boolean = false,
    val allDataLoaded: Boolean = false,
    val error: String? = null
)

class HomeRowVMSlice(
    private val getHomeProductsByTypeUseCase: GetHomeProductsByTypeUseCaseMock,
    private val domainToUiProductMapper: DomainToUiProductMapper,
    getFavoriteProductIds: GetFavoriteProductIds,
    private val type: String,
    parentScope: CoroutineScope
) {
    private val _state: MutableStateFlow<HomeRowState> = MutableStateFlow(
        HomeRowState(
            type = type,
            loading = true
        )
    )

    val state = combine(
        _state,
        getFavoriteProductIds()
    ) { state, favoriteIds ->
        state.copy(
            products = state.products.map { product ->
                product.copy(isFavorite = favoriteIds.contains(product.id))
            }
        )
    }.stateIn(
        scope = parentScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeRowState()
    )

    suspend operator fun invoke() {

        _state.update { it.copy(loading = true) }
        when (val products = getHomeProductsByTypeUseCase(type, state.value.products.size)) {
            is Resource.Error -> {
                _state.update {
                    it.copy(
                        loading = false,
                        error = "error"
                    )
                }
            }

            is Resource.Success -> {
                _state.update {
                    val pastData = it.products
                    it.copy(
                        products = pastData + domainToUiProductMapper.map(products.data),
                        loading = false,
                        allDataLoaded = (products.code == 204)
                    )
                }
            }
        }
    }
}