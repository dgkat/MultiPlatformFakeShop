package core.presentation

import androidx.lifecycle.ViewModel

public actual abstract class KMPViewModel: ViewModel() {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     *
     * On Android this is bound to `Dispatchers.Main.immediate`,
     * where on Apple platforms it is bound to `Dispatchers.Main`.
     */
    @Suppress("LeakingThis")
    public actual val viewModelScope: ViewModelScope = ViewModelScopeImpl(this)
    val a = viewModelScope.coroutineScope
    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public actual override fun onCleared() { }
}