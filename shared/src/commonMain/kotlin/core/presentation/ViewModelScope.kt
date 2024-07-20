package core.presentation

import kotlinx.coroutines.CoroutineScope

public expect interface ViewModelScope

/**
 * Gets the [CoroutineScope] associated with the [KMMViewModel] of `this` [ViewModelScope].
 */
public expect val ViewModelScope.coroutineScope: CoroutineScope