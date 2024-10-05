package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.job
import ro.alexmamo.firestorecleanarchitecture.components.ConfirmButton
import ro.alexmamo.firestorecleanarchitecture.components.DismissButton
import ro.alexmamo.firestorecleanarchitecture.core.Constants.ADD_BOOK
import ro.alexmamo.firestorecleanarchitecture.core.Constants.ADD_BUTTON
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR_NAME
import ro.alexmamo.firestorecleanarchitecture.core.Constants.BOOK_TITLE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.DISMISS_BUTTON
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EMPTY_STRING
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TITLE

@Composable
fun AddBookAlertDialog(
    showEmptyTitleMessage: () -> Unit,
    showEmptyAuthorMessage: () -> Unit,
    addBook: (book: Map<String, Any>) -> Unit,
    closeDialog: () -> Unit
) {
    var title by remember { mutableStateOf(EMPTY_STRING) }
    var author by remember { mutableStateOf(EMPTY_STRING) }
    val focusRequester = FocusRequester()

    AlertDialog(
        onDismissRequest = closeDialog,
        title = {
            Text(
                text = ADD_BOOK
            )
        },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = BOOK_TITLE
                        )
                    },
                    modifier = Modifier.focusRequester(focusRequester)
                )
                LaunchedEffect(Unit) {
                    coroutineContext.job.invokeOnCompletion {
                        focusRequester.requestFocus()
                    }
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                TextField(
                    value = author,
                    onValueChange = { author = it },
                    placeholder = {
                        Text(
                            text = AUTHOR_NAME
                        )
                    }
                )
            }
        },
        confirmButton = {
            ConfirmButton(
                confirmText = ADD_BUTTON,
                confirmAction = {
                    if (title.isEmpty()) {
                        showEmptyTitleMessage()
                    } else if (author.isEmpty()) {
                        showEmptyAuthorMessage()
                    } else {
                        val book = mapOf<String, Any>(
                            AUTHOR to author,
                            TITLE to title
                        )
                        addBook(book)
                        closeDialog()
                    }
                }
            )
        },
        dismissButton = {
            DismissButton(
                dismissText = DISMISS_BUTTON,
                closeDialog = closeDialog
            )
        }
    )
}