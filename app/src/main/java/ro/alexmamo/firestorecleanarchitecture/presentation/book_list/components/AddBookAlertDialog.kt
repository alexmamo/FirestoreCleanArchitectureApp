package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.alexmamo.firestorecleanarchitecture.R
import ro.alexmamo.firestorecleanarchitecture.components.ActionButton
import ro.alexmamo.firestorecleanarchitecture.core.EMPTY_STRING
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.BookError

@Composable
fun AddBookAlertDialog(
    onAddBook: (book: Book) -> Unit,
    onAddBookError: (BookError) -> Unit,
    onAddBookDialogCancel: () -> Unit
) {
    var title by remember { mutableStateOf(EMPTY_STRING) }
    var author by remember { mutableStateOf(EMPTY_STRING) }

    AlertDialog(
        onDismissRequest = onAddBookDialogCancel,
        title = {
            Text(
                text = stringResource(
                    id = R.string.add_book
                )
            )
        },
        text = {
            Column {
                TitleTextField(
                    title = title,
                    onUpdateTitle = { newTitle ->
                        title = newTitle
                    }
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                AuthorTextField(
                    author = author,
                    onUpdateAuthor = { newAuthor ->
                        author = newAuthor
                    }
                )
            }
        },
        confirmButton = {
            ActionButton(
                onActionButtonClick = {
                    if (title.isEmpty()) {
                        onAddBookError(BookError.EmptyTitle)
                        return@ActionButton
                    }
                    if (author.isEmpty()) {
                        onAddBookError(BookError.EmptyAuthor)
                        return@ActionButton
                    }
                    onAddBook(Book(
                        title = title,
                        author = author
                    ))
                    onAddBookDialogCancel()
                },
                resourceId = R.string.add_button
            )
        },
        dismissButton = {
            ActionButton(
                onActionButtonClick = onAddBookDialogCancel,
                resourceId = R.string.cancel_button
            )
        }
    )
}