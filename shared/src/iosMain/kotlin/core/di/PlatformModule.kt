package core.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

actual fun platformModule() = module {

}

actual val platformModules = module {
    includes(networkModule, databaseModule)
}
val networkModule = module {
    single { provideHttpClient() }
}
fun provideHttpClient() = //Android.create()

val databaseModule = module {
    single { provideProductsDatabase(get()) }
    single { get<ProductsDatabase>().productsDao() }
}

fun provideProductsDatabase(): ProductsDatabase {
    val dbFile = NSHomeDirectory() + "/products.db"
    return Room.databaseBuilder<ProductsDatabase>(
        name = dbFile,
        factory = { ProductsDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}

fun provideProductsDao(database: ProductsDatabase) = database.productsDao()