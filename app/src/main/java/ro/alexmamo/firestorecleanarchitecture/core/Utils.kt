package ro.alexmamo.firestorecleanarchitecture.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText

const val TAG = "AppTag"
const val EMPTY_STRING = ""

fun printError(e: Exception?) = e?.apply {
    Log.e(TAG, "$message")
}

fun showMessage(
    context: Context,
    resourceId: Int
) = makeText(context, context.resources.getString(resourceId), LENGTH_LONG).show()