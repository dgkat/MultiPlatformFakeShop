package core.data.remote

import core.domain.models.Product
import core.util.BASE_URL
import core.util.Resource
import core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import org.koin.core.component.KoinComponent

class ProductClientImpl(private val httpClient: HttpClient) : KoinComponent, ProductClient {

    override suspend fun getProductById(): Resource<Product> {
        return safeRequest {
            httpClient.get(BASE_URL) {
                url {
                    appendPathSegments("random_product")
                }
            }
        }
    }
}