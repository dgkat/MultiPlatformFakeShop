package org.dgkat.multiplatform_fake_shop.core.di

import favoritesrecents.sharedPresentation.FavoritesRecentsViewModel
import home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import productMain.presentation.ProductMainViewModel

fun androidModule() = module {
    //TODO split modules
    //Home screen
    viewModel { ProductMainViewModel() }
    viewModel { HomeViewModel(get(), get(), get()) }

    //FavoritesRecents screen
    viewModel(named("favorites")) {
        FavoritesRecentsViewModel(
            get(named("favorites")),
            get(named("favorites"))
        )
    }
    viewModel(named("recently_seen")) {
        FavoritesRecentsViewModel(
            get(named("recently_seen")),
            get(named("recently_seen"))
        )
    }
}