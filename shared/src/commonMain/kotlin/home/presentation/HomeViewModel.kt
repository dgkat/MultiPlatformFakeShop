package home.presentation

import core.domain.models.Product
import core.domain.models.ProductRating
import core.presentation.KMPViewModel
import core.presentation.coroutineScope
import core.domain.util.Resource
import home.domain.GetHomeProductsByTypeUseCase
import home.domain.GetHomeProductsByTypeUseCaseMock
import home.domain.GetHomeProductsByTypesUseCase
import home.presentation.mappers.DomainToUiProductMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val getHomeProductsByTypes: GetHomeProductsByTypesUseCase,
    private val domainToUiProductMapper: DomainToUiProductMapper,
    private val getProductsByTypePaginated: GetProductsByTypePaginated
) : KMPViewModel(), KoinComponent {
    private val scope: CoroutineScope = viewModelScope.coroutineScope

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
        scope.launch { }
        /* a.launch {
             val types = listOf("phone", "laptop", "asdf", "")
             val result = getHomeProductsByTypes(types)
             result.entries.forEach { flowEntry ->
                 this.launch {
                     val a = flowEntry.key
                     flowEntry.value.collect {
                         when (it) {
                             is Resource.Error -> println("testFlow $a -> ${it.errorType}")
                             is Resource.Success -> {
                                 val b = domainToUiProductMapper.map(it.data)
                                 println("testFlow $a -> $b")
                             }
                         }
                     }
                 }
             }
         }*/
        val types = listOf("phone", "laptop", "asdf", "")
        /*types.forEach { type ->
            a.launch {
                val u = GetProductsByTypePaginated(get()).invoke(type)
                u.collect {
                    when (it) {
                        is Resource.Error -> println("testFlow $a -> ${it.errorType}")
                        is Resource.Success -> {
                            val b = domainToUiProductMapper.map(it.data)
                            println("testFlow $a -> $b")
                        }
                    }
                }
            }

        }*/
        typesMap = types.associateWith {

            StateFlowRowStateHandler(get(), a)
        }

        typesMap.forEach { entry ->
            a.launch {
                entry.value.invoke(entry.key)
                entry.value.state.collect {
                    println("stateFlow $entry -> $it")
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
    val products: List<Product> = emptyList(),
    val loading: Boolean = false
)

class StateFlowRowStateHandler(
    private val getHomeProductsByTypeUseCase: GetHomeProductsByTypeUseCaseMock,
    parentScope: CoroutineScope
) {
    private val products = mutableListOf<Product>()

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
                _state.update { it.copy(loading = false) }
            }

            is Resource.Success -> {
                _state.update { it.copy(products = a.data, loading = false) }
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
    /*suspend operator fun invoke(type: String): Flow<Resource<List<Product>>> {
        val a = getFirstBatch(type)
        return flow { }
    }*/

    /*private suspend fun getFirstBatch(type: String): Resource<List<Product>> {

        val a = getHomeProductsByTypeUseCase(type)
        return when (a) {
            is Resource.Error -> a
            is Resource.Success -> {
                products.addAll(a.data)
            }

        }
    }*/
//we could have a state class with data map of type to products and the use case just adds items to list
}