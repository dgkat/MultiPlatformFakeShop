package core.data.util

import android.util.Log
import io.ktor.client.plugins.logging.Logger

actual class CustomHttpLogger : Logger {
    override fun log(message: String) {
        Log.d("ktor", message)
    }
}