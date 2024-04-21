package core.di

import core.data.remote.ProductClient
import core.data.remote.ProductClientImpl
import core.data.mappers.RemoteToDomainProductMapper
import core.data.mappers.RemoteToDomainProductRatingMapper
import core.data.repository.ProductRepositoryImpl
import core.domain.repository.ProductRepository
import core.util.CustomHttpLogger
import home.domain.GetHomeProductsByTypeUseCase
import home.domain.GetHomeProductsByTypeUseCaseTest
import home.domain.GetHomeProductsByTypesUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single<ProductClient> { ProductClientImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }

    factory { RemoteToDomainProductRatingMapper() }
    factory { RemoteToDomainProductMapper(get()) }

    factory { GetHomeProductsByTypeUseCase(get()) }
    factory { GetHomeProductsByTypesUseCase(get()) }


    factory { GetHomeProductsByTypeUseCaseTest(get()) }
}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }


fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }

        if (enableNetworkLogs) {
            install(Logging) {
                logger = CustomHttpLogger()
                level = LogLevel.ALL
            }
        }
    }