package org.dgkat.multiplatform_fake_shop

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
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

        createNotificationChannel(applicationContext)
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

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Product Sync Notifications"
        val descriptionText = "Notifications when new products are synced"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("PRODUCT_SYNC_CHANNEL", name, importance).apply {
            description = descriptionText
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

class NotificationHelper(private val context: Context) {

    fun showProductInsertedNotification(productName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "PRODUCT_SYNC_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_background)  // Replace with your app's icon
            .setContentTitle("Product Synced")
            .setContentText("New product \"$productName\" inserted into the database.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification) // 1 is the notification ID
    }
}