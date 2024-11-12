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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ro.alexmamo.firestorecleanarchitecture.R
import ro.alexmamo.firestorecleanarchitecture.components.ActionButton
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

@Composable
fun EditableBookCard(
    book: Book,
    onUpdateBook: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var updatedBook by remember { mutableStateOf(book) }

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
                title = updatedBook.title.orEmpty(),
                onUpdateTitle = { newTitle ->
                    updatedBook = updatedBook.copy(
                        title = newTitle
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            AuthorTextField(
                author = updatedBook.author.orEmpty(),
                onUpdateAuthor = { newAuthor ->
                    updatedBook = updatedBook.copy(
                        author = newAuthor
                    )
                }
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
                        onUpdateBook(updatedBook)
                    },
                    resourceId = R.string.update_button
                )
            }
        }
    }
}