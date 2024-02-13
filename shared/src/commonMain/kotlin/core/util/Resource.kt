package core.util

/*sealed class Resource<T>(val data: T?, val throwable: Throwable? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(throwable: Throwable): Resource<T>(null, throwable)
}*/
//TODO decide
/*sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    sealed class Error<T>(data: T? = null, message: String? = null) : Resource<T>(data, message) {
        class GenericError<T>(data: T? = null, message: String? = null) : Error<T>()

        sealed class ApiError<T>(message: String? = null, data: T? = null) :
            Resource<T>(data, message) {
            class HttpError<T>(val code: Int, val errorBody: T?) : Error<T>()

            class NetworkError<T> : Error<T>()

            class SerializationError<T> : Error<T>()
        }
    }
}*/
/*

option 2

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String, val errorWrapper: ErrorWrapper<T>? = null) :
        Resource<T>(data, message)
}

sealed class ErrorWrapper<T>(message: String? = null, data: T? = null) :
    Resource<T>(data, message) {
    class GenericError<T>(message: String, data: T?) : ErrorWrapper<T>(
        message,
        data
    )

    sealed class ApiError<T>(message: String? = null) :
        Resource<T>(message = message) {
        class HttpError<T>(val code: Int, val errorBody: T?, message: String) : ErrorWrapper<T>(
            message
        )

        class NetworkError<T> : ErrorWrapper<T>()

        class SerializationError<T> : ErrorWrapper<T>()
    }
}


3rd

 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(
        data: T? = null,
        message: String? = null,
        errorType: FakeShopError = FakeShopError.UnknownError
    ) : Resource<T>(data, message)
}

sealed class FakeShopError {
    data object ServiceUnavailable : FakeShopError()
    data class HttpError(val code: Int, val errorBody: String?) : FakeShopError()
    data object ClientError : FakeShopError()
    data object ServerError : FakeShopError()
    data object NetworkError : FakeShopError()
    data object SerializationError : FakeShopError()
    data object UnknownError : FakeShopError()
}


