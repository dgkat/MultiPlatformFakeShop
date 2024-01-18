package core.data.remote

import core.domain.models.Product
import core.domain.repository.ProductRepository
import core.util.Resource
import core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.appendPathSegments
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductRepositoryImpl : KoinComponent, ProductRepository {
    private val httpClient :HttpClient by inject()
    override suspend fun getProductById(): Resource<Product> {

        /*val localResponse: HttpResponse = httpClient.get("http://10.0.2.2:8080/") {
            url {
                appendPathSegments("random_product")
            }
        }*/
        val localResponse: HttpResponse = httpClient.get("http://10.0.2.2:8080/") {
            url {
                appendPathSegments("random_product")
            }
        }
        val response  = httpClient.safeRequest<Product> {
            this.url{
                appendPathSegments("random_product")
            }
        }
        return Resource.Success(localResponse.body())
    }
}