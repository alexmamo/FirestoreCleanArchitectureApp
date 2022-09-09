package ro.alexmamo.firestorecleanarchitecture.core

import android.util.Log
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception?) {
            Log.e(TAG, e?.message ?: e.toString())
        }
    }
}