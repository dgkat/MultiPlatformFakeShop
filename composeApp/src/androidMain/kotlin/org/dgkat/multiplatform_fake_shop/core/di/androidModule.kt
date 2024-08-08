package org.dgkat.multiplatform_fake_shop.core.di

import favoritesrecents.domain.GetFavoriteProductsImpl
import favoritesrecents.domain.GetFavoriteRecentProducts
import favoritesrecents.domain.GetRecentlySeenProductsImpl
import favoritesrecents.presentation.FavoritesRecentsViewModel
import home.presentation.HomeViewModel
import productMain.presentation.ProductMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun androidModule() = module {
    viewModel { ProductMainViewModel() }
    viewModel { HomeViewModel(get(),get(),get()) }


    factory<GetFavoriteRecentProducts>(named("favorites")) { GetFavoriteProductsImpl() }
    factory<GetFavoriteRecentProducts>(named("recently_seen")) { GetRecentlySeenProductsImpl() }

    viewModel(named("favorites")){ FavoritesRecentsViewModel(get(named("favorites"))) }
    viewModel(named("recently_seen")){ FavoritesRecentsViewModel(get(named("recently_seen"))) }
}