package home.domain

import core.domain.models.Product

interface SaveProduct {
    suspend fun saveProduct(product: Product)
}