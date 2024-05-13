package home.presentation

import core.domain.util.Resource
import core.presentation.KMPViewModel
import home.domain.GetHomeProductsByTypeUseCaseMock
import home.domain.GetHomeProductsByTypesUseCase
import home.presentation.mappers.DomainToUiProductMapper
import home.presentation.models.UiHomeProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeViewModel(
    private val getHomeProductsByTypes: GetHomeProductsByTypesUseCase,
    private val domainToUiProductMapper: DomainToUiProductMapper
) : KMPViewModel(), KoinComponent {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState()
    )
    val state = _state.stateIn(
        scope = a,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    init {
        //check if internet connection to show error / call for types to show

        val types = listOf(
            "phone",
            "laptop",
            "asdf",
            "",
            "this_is_a_long_text_to_represent_many_items_in_a_row"
        )

        val handlers = mutableListOf<StateFlow<HomeRowState>>()
        // create a class that handles this (factory maybe?)( we can then inject the handler there)
        types.forEach { type ->
            val handler = StateFlowRowStateHandler(
                getHomeProductsByTypeUseCase = get(),
                domainToUiProductMapper = domainToUiProductMapper,
                parentScope = a
            )
            handlers.add(
                handler.state
            )
            a.launch {
                handler.invoke(type)
            }
        }
        _state.update {
            it.copy(data = handlers)
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
            }
        }
    }
}

data class HomeRowState(
    val type: String = "",
    val products: List<UiHomeProduct> = emptyList(),
    val loading: Boolean = false
)

class StateFlowRowStateHandler(
    private val getHomeProductsByTypeUseCase: GetHomeProductsByTypeUseCaseMock,
    private val domainToUiProductMapper: DomainToUiProductMapper,
    parentScope: CoroutineScope
) {
    private val _state: MutableStateFlow<HomeRowState> = MutableStateFlow(
        HomeRowState()
    )
    val state = _state.stateIn(
        scope = parentScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeRowState()
    )


    suspend operator fun invoke(type: String) {

        _state.update { it.copy(type = type, loading = true) }
        when (val products = getHomeProductsByTypeUseCase(type)) {
            is Resource.Error -> {
                _state.update { it.copy(type = type, loading = false) }
            }

            is Resource.Success -> {
                _state.update {
                    it.copy(
                        type = type,
                        products = domainToUiProductMapper.map(products.data),
                        loading = false
                    )
                }
            }
        }
    }
}