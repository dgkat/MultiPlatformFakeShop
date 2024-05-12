package core.di

import core.data.local.models.LocalProduct
import core.data.local.models.LocalProductRating
import core.data.local.ProductDatabase
import core.data.local.ProductDatabaseImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

fun databaseModule() = module {
    single<ProductDatabase> { ProductDatabaseImpl(get()) }
    single { createRealmDatabase() }
}

fun createRealmDatabase(): Realm {
    val configuration = RealmConfiguration.create(
        schema = setOf(
            LocalProduct::class,
            LocalProductRating::class
        )
    )

    return Realm.open(configuration = configuration)
}