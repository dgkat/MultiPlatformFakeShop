package org.dgkat.multiplatform_fake_shop

import android.app.Application
import core.di.initKoin
import org.dgkat.multiplatform_fake_shop.core.di.androidModule
import org.koin.android.ext.koin.androidContext

class FakeShopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(androidModule())
        initKoin(enableNetworkLogs = true) {
            androidContext(this@FakeShopApplication)
            modules(appModules)
        }
    }
}