package core.data.remote

import core.util.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments
import org.koin.core.component.KoinComponent

class ProductClientImpl(private val httpClient: HttpClient) : KoinComponent, ProductClient {

    override suspend fun getProductById(): RemoteProduct {
        return httpClient.get(BASE_URL) {
            url {
                appendPathSegments("random_product")
            }
        }.body()

    }

    override suspend fun getProductByType(type: String): List<RemoteProduct> {
        return httpClient.get(BASE_URL) {
            url {
                appendPathSegments("/productByType")
            }
            parameter("type", type)
        }.body()
    }
}