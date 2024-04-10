package core.base

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.WeakReference

public actual abstract class KMPViewModel {
    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     *
     * On Android this is bound to `Dispatchers.Main.immediate`,
     * where on Apple platforms it is bound to `Dispatchers.Main`.
     */

    @OptIn(ExperimentalNativeApi::class)
    @Suppress("LeakingThis")
    actual val viewModelScope: ViewModelScope = ViewModelScopeImpl(WeakReference(this))

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    actual open fun onCleared() {
    }
}