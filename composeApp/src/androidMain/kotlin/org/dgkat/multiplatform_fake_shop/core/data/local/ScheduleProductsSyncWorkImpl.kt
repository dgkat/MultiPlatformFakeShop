package org.dgkat.multiplatform_fake_shop.core.data.local

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import core.data.local.ScheduleProductsSyncWork
import java.util.concurrent.TimeUnit

class ScheduleProductsSyncWorkImpl(private val workManager: WorkManager) :ScheduleProductsSyncWork {
    override operator fun invoke() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .setRequiresStorageNotLow(false)
            .build()

        val productSyncWorkRequest = PeriodicWorkRequestBuilder<ProductsSyncWorker>(
            15, TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "ProductSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            productSyncWorkRequest
        )
        println("WorkManagerTest: Work scheduled")
    }
}