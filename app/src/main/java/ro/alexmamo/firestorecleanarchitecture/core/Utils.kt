package ro.alexmamo.firestorecleanarchitecture.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.compose.material.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val TAG = "AppTag"
const val AUTHOR_FIELD = "author"
const val ID_FIELD = "id"
const val TITLE_FIELD = "title"
const val NO_BOOK_AUTHOR = "No book author"
const val NO_BOOK_TITLE = "No book title"

fun logMessage(
    message: String
) = Log.e(TAG, message)

fun showToastMessage(
    context: Context,
    message: String
) = makeText(context, message, LENGTH_LONG).show()

fun showSnackbarMessage(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String
) = coroutineScope.launch {
    snackbarHostState.showSnackbar(message)
}