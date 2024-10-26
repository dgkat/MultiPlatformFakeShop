package productDetail.presentation

import core.domain.models.Product

data class ProductDetailState(
    val data : Product? = null,
    val loading: Boolean = false
)