package core.di

import core.data.mappers.DomainToLocalProductMapper
import core.data.mappers.DomainToLocalProductRatingMapper
import core.data.mappers.LocalToDomainProductMapper
import core.data.mappers.LocalToDomainProductRatingMapper
import core.data.mappers.RemoteToDomainProductMapper
import core.data.mappers.RemoteToDomainProductRatingMapper
import core.data.remote.ProductClient
import core.data.remote.ProductClientImpl
import core.data.repository.ProductRepositoryImpl
import core.data.util.CustomHttpLogger
import core.domain.repository.ProductRepository
import home.domain.GetHomeProductsByTypeUseCase
import home.domain.GetHomeProductsByTypeUseCaseMock
import home.domain.GetHomeProductsByTypesUseCase
import home.presentation.mappers.DomainToUiProductMapper
import home.presentation.mappers.DomainToUiProductRatingMapper
import home.presentation.mappers.UiToDomainProductMapper
import home.presentation.mappers.UiToDomainProductRatingMapper
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
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModules)
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single<ProductClient> { ProductClientImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get(), get(), get()) }

    factory { RemoteToDomainProductRatingMapper() }
    factory { RemoteToDomainProductMapper(get()) }

    factory { LocalToDomainProductRatingMapper() }
    factory { LocalToDomainProductMapper(get()) }

    factory { DomainToLocalProductRatingMapper() }
    factory { DomainToLocalProductMapper(get()) }

    factory { DomainToUiProductRatingMapper() }
    factory { DomainToUiProductMapper(get()) }

    factory { UiToDomainProductRatingMapper() }
    factory { UiToDomainProductMapper(get()) }

    factory { GetHomeProductsByTypeUseCase(get()) }
    factory { GetHomeProductsByTypesUseCase(get()) }

    factory { GetHomeProductsByTypeUseCaseMock(get()) }
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