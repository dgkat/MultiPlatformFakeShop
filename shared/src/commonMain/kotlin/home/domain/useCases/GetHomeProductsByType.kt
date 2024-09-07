package home.domain.useCases

import core.domain.models.Product
import core.domain.util.Resource

interface GetHomeProductsByType {
    suspend operator fun invoke(type: String, pageFrom: Int): Resource<List<Product>>
}