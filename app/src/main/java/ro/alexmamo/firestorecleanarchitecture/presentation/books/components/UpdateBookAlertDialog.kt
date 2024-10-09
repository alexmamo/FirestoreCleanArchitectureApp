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
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR_NAME
import ro.alexmamo.firestorecleanarchitecture.core.Constants.BOOK_TITLE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.DISMISS_BUTTON
import ro.alexmamo.firestorecleanarchitecture.core.Constants.UPDATE_BOOK
import ro.alexmamo.firestorecleanarchitecture.core.Constants.UPDATE_BUTTON
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

@Composable
fun UpdateBookAlertDialog(
    book: Book,
    showEmptyTitleMessage: () -> Unit,
    showEmptyAuthorMessage: () -> Unit,
    updateBook: (book: Book) -> Unit,
    showNoUpdatesMessage: () -> Unit,
    closeDialog: () -> Unit
) {
    var author by remember { mutableStateOf(book.author.orEmpty()) }
    var title by remember { mutableStateOf(book.title.orEmpty()) }

    AlertDialog(
        onDismissRequest = closeDialog,
        title = {
            Text(
                text = UPDATE_BOOK
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
                onConfirmButtonClick = {
                    if (title.isEmpty()) {
                        showEmptyTitleMessage()
                    } else if (author.isEmpty()) {
                        showEmptyAuthorMessage()
                    } else {
                        val updatedBook = Book(
                            id = book.id,
                            author = author,
                            title = title
                        )
                        if (updatedBook != book) {
                            updateBook(updatedBook)
                        } else {
                            showNoUpdatesMessage()
                        }
                        closeDialog()
                    }
                }
            )
        },
        dismissButton = {
            DismissButton(
                dismissText = DISMISS_BUTTON,
                onDismissButtonClick = closeDialog
            )
        }
    )
}