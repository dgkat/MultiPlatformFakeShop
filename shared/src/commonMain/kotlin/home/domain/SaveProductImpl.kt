package home.domain

import core.domain.models.Product
import home.domain.repository.HomeRepository

class SaveProductImpl(private val homeRepository: HomeRepository) : SaveProduct {
    override suspend fun saveProduct(product: Product) {
        homeRepository.saveProductToDB(product)
    }
}