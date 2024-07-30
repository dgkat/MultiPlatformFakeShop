package core.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import core.data.local.ProductsDatabase
import io.ktor.client.engine.android.Android
import org.koin.dsl.module


val networkModule = module {
    single { provideHttpClient() }
}

fun provideHttpClient() = Android.create()

val databaseModule = module {
    single { provideProductsDatabase(get()) }
    single { get<ProductsDatabase>().productsDao() }
}
actual val platformModules = module {
    includes(networkModule, databaseModule)
}

fun provideProductsDatabase(context: Context): ProductsDatabase {
    val dbFile = context.getDatabasePath("products.db")
    return Room.databaseBuilder<ProductsDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}

fun provideProductsDao(database: ProductsDatabase) = database.productsDao()