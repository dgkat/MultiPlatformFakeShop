package core.data.remote

import core.domain.models.Product
import core.domain.repository.ProductRepository
import core.util.Resource
import core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//device -> http://192.168.0.112:8081/
// ipconfig in command prompt -> Link-local IPv6 Address
// emulator -> http://10.0.2.2:8081/
class ProductRepositoryImpl : KoinComponent, ProductRepository {
    private val httpClient: HttpClient by inject()
    override suspend fun getProductById(): Resource<Product> {
        /*val response = try {
            Resource.Success(
                httpClient.get("http://192.168.0.112:8081/") {
                    url {
                        appendPathSegments("random_product")
                    }
                }.body<Product>()
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            Resource.Error()
        }

        return response*/
        /*val localResponse: HttpResponse = httpClient.get("http://10.0.2.2:8080/") {
            url {
                appendPathSegments("random_product")
            }
        }*/
        /*val response  = httpClient.safeRequest<Product>(url = "http://192.168.0.110:8081/") {
            method = HttpMethod.Get
            url{
                appendPathSegments("random_product")
            }
        }*/


        return safeRequest {
            httpClient.get("http://192.168.0.112:8081/") {
                url {
                    appendPathSegments("random_product")
                }
            }
        }
    }
}