package org.dgkat.multiplatform_fake_shop.core.di

import productMain.presentation.ProductMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun androidModule() = module {
    viewModel{ ProductMainViewModel() }
}