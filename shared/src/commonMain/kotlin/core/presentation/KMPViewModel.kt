package core.presentation

public expect abstract class KMPViewModel() {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     *
     * On Android this is bound to `Dispatchers.Main.immediate`,
     * where on Apple platforms it is bound to `Dispatchers.Main`.
     */
    public val viewModelScope: core.presentation.ViewModelScope

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public open fun onCleared()
}