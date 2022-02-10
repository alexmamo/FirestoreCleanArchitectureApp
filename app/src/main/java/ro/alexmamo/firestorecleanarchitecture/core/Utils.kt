package ro.alexmamo.firestorecleanarchitecture.core

import android.util.Log
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TAG

class Utils {
    companion object {
        fun printError(message: String) {
            Log.d(TAG, message)
        }
    }
}