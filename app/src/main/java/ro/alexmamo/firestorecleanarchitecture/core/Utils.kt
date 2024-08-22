package ro.alexmamo.firestorecleanarchitecture.core

import android.util.Log
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TAG

class Utils {
    companion object {
        fun printError(e: Exception?) = e?.apply {
            Log.e(TAG, "$message")
        }
    }
}