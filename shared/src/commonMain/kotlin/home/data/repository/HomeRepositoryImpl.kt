package home.data.repository

import core.data.local.ProductsDao
import core.data.local.ScheduleProductsSyncWork
import core.data.mappers.DomainToLocalProductMapper
import core.data.mappers.LocalToDomainProductMapper
import core.data.mappers.RemoteToDomainProductMapper
import core.data.remote.ProductClient
import core.data.util.safeRequest
import core.domain.models.Product
import home.domain.repository.HomeRepository
import core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class HomeRepositoryImpl(
    private val productClient: ProductClient,
    private val remoteToDomainProductMapper: RemoteToDomainProductMapper,
    private val productDao: ProductsDao,
    private val localToDomainProductMapper: LocalToDomainProductMapper,
    private val domainToLocalProductMapper: DomainToLocalProductMapper,
    private val scheduleProductsSyncWork: ScheduleProductsSyncWork
) : KoinComponent,
    HomeRepository {

        init {
            scheduleProductsSyncWork.invoke()
        }

    override suspend fun getProductById(): Resource<Product> {
        val response = productClient.getProductById()
        return safeRequest { remoteToDomainProductMapper.map(response) }
    }

    override suspend fun getProductsByType(type: String): Resource<List<Product>> {
        return safeRequest {
            val response = productClient.getProductByType(type)
            remoteToDomainProductMapper.map(response)
        }
    }

    override suspend fun saveProductToDB(product: Product) {
        productDao.upsertProduct(domainToLocalProductMapper.map(product))
    }

    override fun getFavoriteProductIdsFromDb(): Flow<List<Int>> {
        return productDao.getFavoriteProductIds()
    }

}