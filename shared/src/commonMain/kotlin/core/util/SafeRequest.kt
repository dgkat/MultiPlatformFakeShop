package core.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

/*
suspend inline fun <reified T> HttpClient.safeRequest(
    url: String? = null,
    block: HttpRequestBuilder.() -> Unit,
): Resource<T> =
    try {
        val response = request(urlString = url ?: "") { block() }
        Resource.Success(response.body())
    } catch (e: ClientRequestException) {
        Resource.Error.ApiError.HttpError(
            code = e.response.status.value,
            errorBody = e.errorBody()
        )
    } catch (e: ServerResponseException) {
        Resource.Error.ApiError.HttpError(
            code = e.response.status.value,
            errorBody = e.errorBody()
        )
    } catch (e: IOException) {
        Resource.Error.ApiError.NetworkError()
    } catch (e: SerializationException) {
        Resource.Error.ApiError.SerializationError()
    } catch (e: ResponseException) {
        Resource.Error.GenericError()
    }
*/

/*
suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {
        response.body()
    } catch (e: SerializationException) {
        null
    }*/
suspend inline fun <reified T> safeRequest(
    crossinline response: suspend () -> HttpResponse
): Resource<T> =
    try {
        Resource.Success(response().body())
    } catch (e: ClientRequestException) {
        Resource.Error(
            errorType =
            FakeShopError.HttpError(
                code = e.response.status.value,
                errorBody = e.response.status.description
            )
        )
    } catch (e: ServerResponseException) {
        Resource.Error(
            errorType =
            FakeShopError.HttpError(
                code = e.response.status.value,
                errorBody = e.response.status.description
            )
        )
    } catch (e: IOException) {
        Resource.Error(
            errorType = FakeShopError.NetworkError
        )
    } catch (e: SerializationException) {
        Resource.Error(
            errorType = FakeShopError.SerializationError
        )
    } catch (e: ResponseException) {
        Resource.Error(
            errorType = FakeShopError.ClientError
        )
    } catch (e: Exception) {
        Resource.Error()
    }