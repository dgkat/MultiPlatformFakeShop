package home.domain

import core.domain.models.Product
import core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*class GetHomeProductsByTypesUseCase(
    private val getHomeProductsByTypeUseCase: GetHomeProductsByType
) {
    suspend operator fun invoke(types: List<String>): Map<String, Flow<Resource<List<Product>>>> {
        val productsMap =
            types.associateWith {
                flow {
                    emit(
                        getHomeProductsByTypeUseCase(it,5)
                    )
                }
            }
        return productsMap
    }
}*/


