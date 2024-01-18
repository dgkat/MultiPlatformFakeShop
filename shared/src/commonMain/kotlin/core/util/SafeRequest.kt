package core.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): Resource<T> =
    try {
        val response = request { block() }
        Resource.Success(response.body())
    } catch (e: ClientRequestException) {
        Resource.Error.ApiError.HttpError(
            code = e.response.status.value,
            errorBody = e.errorBody(),
            message = ""
        )
    } catch (e: ServerResponseException) {
        Resource.Error.ApiError.HttpError(
            code = e.response.status.value,
            errorBody = e.errorBody(),
            message = ""
        )
    } catch (e: IOException) {
        Resource.Error.ApiError.NetworkError()
    } catch (e: SerializationException) {
        Resource.Error.ApiError.SerializationError()
    }

suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {
        response.body()
    } catch (e: SerializationException) {
        null
    }