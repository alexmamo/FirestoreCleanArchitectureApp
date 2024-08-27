package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ro.alexmamo.firestorecleanarchitecture.components.ConfirmButton
import ro.alexmamo.firestorecleanarchitecture.components.DismissButton
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR_NAME
import ro.alexmamo.firestorecleanarchitecture.core.Constants.BOOK_TITLE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.DISMISS_BUTTON
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EDIT_BOOK
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TITLE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.UPDATE_BUTTON

@Composable
fun UpdateBookAlertDialog(
    showEmptyTitleMessage: () -> Unit,
    showEmptyAuthorMessage: () -> Unit,
    updateBook: (id: String, book: MutableMap<String, Any>) -> Unit,
    closeDialog: () -> Unit
) {
    GetBook(
        bookContent = { selectedBook ->
            var author by remember { mutableStateOf(selectedBook.author.orEmpty()) }
            var title by remember { mutableStateOf(selectedBook.title.orEmpty()) }

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
                                title = newTitle
                            },
                            placeholder = {
                                Text(
                                    text = BOOK_TITLE
                                )
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                        TextField(
                            value = author,
                            onValueChange = { newAuthor ->
                                author = newAuthor
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
                        confirmAction = {
                            if (title.isEmpty()) {
                                showEmptyTitleMessage()
                            } else if (author.isEmpty()) {
                                showEmptyAuthorMessage()
                            } else {
                                val bookUpdates = mutableMapOf<String, Any>().apply {
                                    if (selectedBook.author != author) {
                                        put(AUTHOR, author)
                                    }
                                    if (selectedBook.title != title) {
                                        put(TITLE, title)
                                    }
                                }
                                selectedBook.id?.let { id ->
                                    updateBook(id, bookUpdates)
                                    closeDialog()
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