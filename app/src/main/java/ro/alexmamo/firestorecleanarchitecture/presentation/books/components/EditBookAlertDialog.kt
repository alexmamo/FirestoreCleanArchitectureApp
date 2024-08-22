package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.job
import ro.alexmamo.firestorecleanarchitecture.components.ConfirmButton
import ro.alexmamo.firestorecleanarchitecture.components.DismissButton
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR_NAME
import ro.alexmamo.firestorecleanarchitecture.core.Constants.BOOK_TITLE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.DISMISS_BUTTON
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EDIT_BOOK
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TAG
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TITLE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.UPDATE_BUTTON

@Composable
fun EditBookAlertDialog(
    editBook: (id: String, book: MutableMap<String, Any>) -> Unit,
    closeDialog: () -> Unit
) {
    val focusRequester = FocusRequester()

    GetBook(
        bookContent = { selectedBook ->
            var author by remember { mutableStateOf(TextFieldValue(selectedBook.author.orEmpty())) }
            var title by remember { mutableStateOf(TextFieldValue(selectedBook.title.orEmpty())) }
            Log.d(TAG, "GetBook: " + selectedBook.id.toString())

            AlertDialog(
                onDismissRequest = closeDialog,
                title = {
                    Text(
                        text = EDIT_BOOK
                    )
                },
                text = {
                    Column {
                        TextField(
                            value = title,
                            onValueChange = { newTitle ->
                                title = TextFieldValue(
                                    text = newTitle.text,
                                    selection = TextRange(
                                        newTitle.text.length
                                    )
                                )
                            },
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
                            onValueChange = { newAuthor ->
                                author = TextFieldValue(
                                    text = newAuthor.text,
                                    selection = TextRange(
                                        newAuthor.text.length
                                    )
                                )
                            },
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
                        confirmText = UPDATE_BUTTON,
                        closeDialog = closeDialog,
                        confirmAction = {
                            val bookUpdates = mutableMapOf<String, Any>()
                            if (selectedBook.author != author.text) {
                                bookUpdates[AUTHOR] = author.text
                            }
                            if (selectedBook.title != title.text) {
                                bookUpdates[TITLE] = title.text
                            }
                            if (bookUpdates.isNotEmpty()) {
                                selectedBook.id?.let { id ->
                                    editBook(id, bookUpdates)
                                }
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
    )
}