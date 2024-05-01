package home.presentation

import core.presentation.KMPViewModel
import core.presentation.coroutineScope
import core.domain.util.Resource
import home.domain.GetHomeProductsByTypesUseCase
import home.presentation.mappers.DomainToUiProductMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class HomeViewModel(
    private val getHomeProductsByTypes: GetHomeProductsByTypesUseCase,
    private val domainToUiProductMapper: DomainToUiProductMapper
) : KMPViewModel(), KoinComponent {
    private val scope: CoroutineScope = viewModelScope.coroutineScope
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState()
    )
    val state = _state.stateIn(
        scope = a,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    init {
        scope.launch { }
        a.launch {
            val types = listOf("phone", "laptop", "asdf","")
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
            /*val result = getHomeProductsByType("test")
            println("result $result")
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        HomeState(result.errorType?.toString() ?: "oops")
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        HomeState(result.data.firstOrNull()?.title ?: "no title")
                    }
                }
            }*/

        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProductClicked -> {}
        }
    }
}