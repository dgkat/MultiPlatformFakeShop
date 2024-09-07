package core.di

import IS_PROD
import core.data.mappers.DomainToLocalProductMapper
import core.data.mappers.DomainToLocalProductRatingMapper
import core.data.mappers.LocalToDomainProductMapper
import core.data.mappers.LocalToDomainProductRatingMapper
import core.data.mappers.RemoteToDomainProductMapper
import core.data.mappers.RemoteToDomainProductRatingMapper
import core.data.remote.ProductClient
import core.data.remote.ProductClientImpl
import core.data.util.CustomHttpLogger
import favoritesrecents.di.favoritesRecentsModule
import home.data.repository.HomeRepositoryImpl
import home.domain.useCases.GetFavoriteProductIds
import home.domain.useCases.GetFavoriteProductIdsImpl
import home.domain.useCases.GetHomeProductsByType
import home.domain.useCases.GetHomeProductsByTypeImpl
import home.domain.useCases.GetHomeProductsByTypeMock
import home.domain.useCases.SaveProduct
import home.domain.useCases.SaveProductImpl
import home.domain.repository.HomeRepository
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
        modules(
            commonModule(enableNetworkLogs = enableNetworkLogs),
            platformModules,
            favoritesRecentsModule()
        )
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single<ProductClient> { ProductClientImpl(get()) }
    factory<HomeRepository> { HomeRepositoryImpl(get(), get(), get(), get(), get(),get()) }

    //Data mappers
    factory { RemoteToDomainProductRatingMapper() }
    factory { RemoteToDomainProductMapper(get()) }

    factory { LocalToDomainProductRatingMapper() }
    factory { LocalToDomainProductMapper(get()) }

    factory { DomainToLocalProductRatingMapper() }
    factory { DomainToLocalProductMapper(get()) }

    //Pres mappers
    factory { DomainToUiProductRatingMapper() }
    factory { DomainToUiProductMapper(get()) }

    factory { UiToDomainProductRatingMapper() }
    factory { UiToDomainProductMapper(get()) }

    //Domain
    factory<GetHomeProductsByType> {
        if (IS_PROD) {
            GetHomeProductsByTypeImpl(get())
        } else {
            GetHomeProductsByTypeMock(get())
        }
    }
    factory<GetFavoriteProductIds> { GetFavoriteProductIdsImpl(get()) }
    factory<SaveProduct> { SaveProductImpl(get()) }


    //FAVES
    /*val appModule = module {
        single<DataRepository>(named("local")) { LocalDataRepository() }
        single<DataRepository>(named("remote")) { RemoteDataRepository() }
    }

    val viewModelModule = module {
        viewModel { (type: String) ->
            val repository: DataRepository = get(named(type))
            MyViewModel(repository)
        }
    }*/
    /*
        factory<GetFavoriteRecentProducts>(named("favorites")) { GetFavoriteProductsImpl() }
        factory<GetFavoriteRecentProducts>(named("recently_seen")) { GetRecentlySeenProductsImpl() }*/
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