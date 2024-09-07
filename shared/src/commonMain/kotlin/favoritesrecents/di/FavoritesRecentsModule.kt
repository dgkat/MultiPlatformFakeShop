package favoritesrecents.di

import favoritesrecents.favorites.data.repository.FavoritesRepositoryImpl
import favoritesrecents.favorites.domain.repository.FavoritesRepository
import favoritesrecents.favorites.domain.useCases.GetFavoriteProductsImpl
import favoritesrecents.favorites.domain.useCases.UnfavoriteProductImpl
import favoritesrecents.recents.data.RecentsRepositoryImpl
import favoritesrecents.recents.domain.repository.RecentsRepository
import favoritesrecents.recents.domain.useCases.GetRecentlySeenProductsImpl
import favoritesrecents.recents.domain.useCases.UpdateFavoriteStatusImpl
import favoritesrecents.sharedDomain.GetFavoriteRecentProducts
import favoritesrecents.sharedDomain.UpdateFavoriteRecentProduct
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun favoritesRecentsModule() = module {
    //Data
    factory<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
    factory<RecentsRepository> { RecentsRepositoryImpl(get(), get()) }

    //Domain
    factory<GetFavoriteRecentProducts>(named("favorites")) { GetFavoriteProductsImpl(get()) }
    factory<UpdateFavoriteRecentProduct>(named("favorites")) { UnfavoriteProductImpl(get()) }

    factory<GetFavoriteRecentProducts>(named("recently_seen")) { GetRecentlySeenProductsImpl(get()) }
    factory<UpdateFavoriteRecentProduct>(named("recently_seen")) { UpdateFavoriteStatusImpl(get()) }
}