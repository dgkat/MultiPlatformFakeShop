package core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalProduct::class], version = 1)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}