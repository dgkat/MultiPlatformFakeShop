package org.dgkat.multiplatform_fake_shop

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import core.di.initKoin
import org.dgkat.multiplatform_fake_shop.core.di.androidModule
import org.koin.android.ext.koin.androidContext

class FakeShopApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(androidModule())
        initKoin(enableNetworkLogs = true) {
            androidContext(this@FakeShopApplication)
            modules(appModules)
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.2)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("fake_shop_image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}