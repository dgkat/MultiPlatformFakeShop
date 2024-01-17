package productMain.presentation

import core.domain.models.Product

data class ProductMainState(
    val data : Product? = null,
    val loading: Boolean = false
)