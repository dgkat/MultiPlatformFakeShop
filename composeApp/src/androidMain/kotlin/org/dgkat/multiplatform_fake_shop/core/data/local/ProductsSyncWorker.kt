package org.dgkat.multiplatform_fake_shop.core.data.local

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import core.data.local.LocalProduct
import core.data.local.LocalProductRating
import core.data.local.ProductsDao
import org.dgkat.multiplatform_fake_shop.NotificationHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class ProductsSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    // Inject ProductsDao using Koin
    private val productsDao: ProductsDao by inject()
    private val notificationHelper: NotificationHelper by inject()

    override suspend fun doWork(): Result {
        try {
            val currentDate = Calendar.getInstance()

            // Get current time components
            val day = currentDate.get(Calendar.DAY_OF_MONTH)
            val month = currentDate.get(Calendar.MONTH) + 1 // Months are 0-indexed
            val hour = currentDate.get(Calendar.HOUR_OF_DAY)
            val minute = currentDate.get(Calendar.MINUTE)

            // Format current time for name
            val dateFormat = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
            val currentTimeString = dateFormat.format(currentDate.time)

            // Generate a 8-digit ID: "99DDHHMM"
            val id = "99%02d%02d%02d".format(day, hour, minute).toInt()

            // Create a new LocalProduct with current time and test data
            val newProduct = LocalProduct(
                name = currentTimeString,
                productType = "WorkManagerTest",
                id = id,
                category = "Test Category",
                description = "Test Description",
                image = "Test Image URL",
                price = Random.nextDouble(10.0, 100.0), // Random price for testing
                rating = LocalProductRating(
                    rate = Random.nextDouble(1.0, 5.0), // Random rating
                    count = Random.nextInt(1, 100) // Random review count
                ),
                isFavorite = true
            )

            // Insert into database
            productsDao.upsertProduct(newProduct)
            println("WorkManagerTest: Product inserted into database: $newProduct")

            notificationHelper.showProductInsertedNotification(newProduct.name)

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            println("WorkManagerTest: Error inserting product into database")
            return Result.failure()
        }
    }
}
