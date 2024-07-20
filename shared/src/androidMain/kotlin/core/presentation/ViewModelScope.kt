package core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

public actual interface ViewModelScope

/**
 * Gets the [CoroutineScope] associated with the [KMPViewModel] of `this` [ViewModelScope].
 */
public actual val ViewModelScope.coroutineScope: CoroutineScope
    get() = (this as ViewModelScopeImpl).coroutineScope

internal class ViewModelScopeImpl(viewModel: KMPViewModel): ViewModelScope {
    val coroutineScope: CoroutineScope = (viewModel as ViewModel).viewModelScope
}