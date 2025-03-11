package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ro.alexmamo.firestorecleanarchitecture.R
import ro.alexmamo.firestorecleanarchitecture.components.ActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.BookField

@Composable
fun EditableBookCard(
    title: TextFieldValue,
    onTitleToUpdateChange: (TextFieldValue) -> Unit,
    author: TextFieldValue,
    onAuthorToUpdateChange: (TextFieldValue) -> Unit,
    onUpdateBook: (String, String) -> Unit,
    onInvalidBookField: (BookField) -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(
            start = 8.dp,
            top = 4.dp,
            end = 8.dp,
            bottom = 4.dp
        ),
        shape = MaterialTheme.shapes.small,
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            TitleTextField(
                title = title,
                onTitleChange = onTitleToUpdateChange
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            AuthorTextField(
                author = author,
                onAuthorChange = onAuthorToUpdateChange
            )
            Row {
                ActionButton(
                    onActionButtonClick = onCancel,
                    resourceId = R.string.cancel_button
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                ActionButton(
                    onActionButtonClick = {
                        val isTitleValid = title.text.isNotBlank()
                        val isAuthorValid = author.text.isNotBlank()
                        if (!isTitleValid) {
                            onInvalidBookField(BookField.TITLE)
                        } else if (!isAuthorValid) {
                            onInvalidBookField(BookField.AUTHOR)
                        } else {
                            onUpdateBook(title.text, author.text)
                        }
                    },
                    resourceId = R.string.update_button
                )
            }
        }
    }
}