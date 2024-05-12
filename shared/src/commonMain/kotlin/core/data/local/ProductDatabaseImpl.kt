package core.data.local

import core.data.local.models.LocalProduct
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class ProductDatabaseImpl(private val database: Realm) : ProductDatabase {
    override suspend fun saveProduct(product: LocalProduct) {
        database.write {
            copyToRealm(product)
        }
    }

    override suspend fun deleteProductById(id: Int) {
        database.write {
            val product =
                this.query<LocalProduct>("_id == $0", id).find().firstOrNull() ?: return@write
            delete(product)
        }
    }

    override suspend fun getProductById(id: Int): LocalProduct {
        val query = database.query<LocalProduct>().first()
        //TODO make nullable and handle error with result ?
        return query.find() ?: LocalProduct()
    }

    override suspend fun getAllFavorites(): List<LocalProduct> {
        TODO("Not yet implemented")
    }
}