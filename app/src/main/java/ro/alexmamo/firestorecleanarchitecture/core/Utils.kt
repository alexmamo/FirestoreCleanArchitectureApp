package ro.alexmamo.firestorecleanarchitecture.core

import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "AppTag"
const val AUTHOR_FIELD = "author"
const val ID_FIELD = "id"
const val TITLE_FIELD = "title"
const val NO_BOOK_AUTHOR = "No book author"
const val NO_BOOK_TITLE = "No book title"

fun logErrorMessage(
    errorMessage: String
) = Log.e(TAG, errorMessage)

fun showToastMessage(
    context: Context,
    message: String
) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()