package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ro.alexmamo.firestorecleanarchitecture.R
import ro.alexmamo.firestorecleanarchitecture.components.ActionButton
import ro.alexmamo.firestorecleanarchitecture.core.AUTHOR_FIELD
import ro.alexmamo.firestorecleanarchitecture.core.TITLE_FIELD
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.BookField
import kotlin.String

const val EMPTY_STRING = ""

@Composable
fun AddBookAlertDialog(
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    author: TextFieldValue,
    onAuthorChange: (TextFieldValue) -> Unit,
    onAddBook: (book: Map<String, String>) -> Unit,
    onInvalidBookField: (BookField) -> Unit,
    onAddBookDialogCancel: () -> Unit
) {
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
                    onTitleChange = onTitleChange
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                AuthorTextField(
                    author = author,
                    onAuthorChange = onAuthorChange
                )
            }
        },
        confirmButton = {
            ActionButton(
                onActionButtonClick = {
                    val isTitleValid = title.text.isNotBlank()
                    val isAuthorValid = author.text.isNotBlank()
                    if (!isTitleValid) {
                        onInvalidBookField(BookField.TITLE)
                        return@ActionButton
                    }
                    if (!isAuthorValid) {
                        onInvalidBookField(BookField.AUTHOR)
                        return@ActionButton
                    }
                    val book = mapOf<String, String>(
                        AUTHOR_FIELD to author.text,
                        TITLE_FIELD to title.text
                    )
                    onAddBook(book)
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