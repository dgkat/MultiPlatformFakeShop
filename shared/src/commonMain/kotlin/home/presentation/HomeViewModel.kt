package home.presentation

import core.domain.models.Product
import core.presentation.KMPViewModel
import core.domain.util.Resource
import home.domain.GetHomeProductsByTypeUseCaseMock
import home.domain.GetHomeProductsByTypesUseCase
import home.presentation.mappers.DomainToUiProductMapper
import home.presentation.models.UiHomeProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeViewModel(
    private val getHomeProductsByTypes: GetHomeProductsByTypesUseCase,
    private val domainToUiProductMapper: DomainToUiProductMapper,
    private val getProductsByTypePaginated: GetProductsByTypePaginated
) : KMPViewModel(), KoinComponent {

    private var typesMap: Map<String, StateFlowRowStateHandler>

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
        val types = listOf("phone", "laptop", "asdf", "")

        typesMap = types.associateWith {

            StateFlowRowStateHandler(
                getHomeProductsByTypeUseCase = get(),
                domainToUiProductMapper = get(),
                parentScope = a
            )
        }

        typesMap.forEach { entry ->
            a.launch {
                entry.value.invoke(entry.key)
                entry.value.state.collect { rowState ->
                    _state.update { currentState ->

                        val newState = currentState.data.toMutableMap().apply {
                            this[rowState.type] = rowState
                        }.toMap()

                        currentState.copy(data = newState)
                    }
                }
            }
        }

        a.launch {
            delay(6000)
            typesMap["asdf"]?.invoke("asdf")
        }

    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClicked -> {}
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

        _state.update { it.copy(loading = true) }
        val a = getHomeProductsByTypeUseCase(type)
        when (a) {
            is Resource.Error -> {
                _state.update { it.copy(type = type, loading = false) }
            }

            is Resource.Success -> {
                _state.update {
                    it.copy(
                        products = domainToUiProductMapper.map(a.data),
                        loading = false
                    )
                }
            }
        }
    }
}

class GetProductsByTypePaginated(
    private val getHomeProductsByTypeUseCase: GetHomeProductsByTypeUseCaseMock,
) {
    private val products = mutableListOf<Product>()


    suspend operator fun invoke(type: String): Flow<Resource<List<Product>>> {
        return flow {
            val a = getPaginated(type)
            emit(
                //getHomeProductsByTypeUseCase(type)
                a
            )
        }
    }


    private suspend fun getPaginated(type: String): Resource<List<Product>> {
        val a = getHomeProductsByTypeUseCase(type)
        return when (a) {
            is Resource.Error -> a
            is Resource.Success -> {
                products.addAll(a.data)
                Resource.Success(data = products)
            }
        }
    }
//we could have a state class with data map of type to products and the use case just adds items to list
}