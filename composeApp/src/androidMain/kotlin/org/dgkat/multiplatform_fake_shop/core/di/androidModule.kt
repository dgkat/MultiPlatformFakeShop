package org.dgkat.multiplatform_fake_shop.core.di

import androidx.work.WorkManager
import core.data.local.ScheduleProductsSyncWork
import favoritesrecents.sharedPresentation.FavoritesRecentsViewModel
import home.presentation.HomeViewModel
import org.dgkat.multiplatform_fake_shop.NotificationHelper
import org.dgkat.multiplatform_fake_shop.core.data.local.ProductsSyncWorker
import org.dgkat.multiplatform_fake_shop.core.data.local.ScheduleProductsSyncWorkImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import productMain.presentation.ProductMainViewModel

fun androidModule() = module {
    //TODO split modules

    //Data

    single { NotificationHelper(get()) }

    factory { ProductsSyncWorker(get(), get()) }

    single { WorkManager.getInstance(androidContext()) }
    single<ScheduleProductsSyncWork> { ScheduleProductsSyncWorkImpl(get()) }
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