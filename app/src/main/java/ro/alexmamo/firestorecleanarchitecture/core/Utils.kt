package ro.alexmamo.firestorecleanarchitecture.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TAG

fun printError(e: Exception?) = e?.apply {
    Log.e(TAG, "$message")
}

fun toastMessage(
    context: Context,
    message: String
) = makeText(context, message, LENGTH_LONG).show()