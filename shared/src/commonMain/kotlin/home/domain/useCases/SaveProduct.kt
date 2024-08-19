package home.domain.useCases

import core.domain.models.Product

interface SaveProduct {
    suspend fun saveProduct(product: Product)
}