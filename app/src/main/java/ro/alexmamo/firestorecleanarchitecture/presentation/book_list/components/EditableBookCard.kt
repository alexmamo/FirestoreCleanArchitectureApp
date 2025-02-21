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
import ro.alexmamo.firestorecleanarchitecture.core.AUTHOR_FIELD
import ro.alexmamo.firestorecleanarchitecture.core.ID_FIELD
import ro.alexmamo.firestorecleanarchitecture.core.NO_BOOK_AUTHOR
import ro.alexmamo.firestorecleanarchitecture.core.NO_BOOK_TITLE
import ro.alexmamo.firestorecleanarchitecture.core.TITLE_FIELD
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

@Composable
fun EditableBookCard(
    book: Book,
    onUpdateBook: (Map<String, String>) -> Unit,
    onEmptyBookField: (String) -> Unit,
    onNoUpdates: () -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(book.title ?: NO_BOOK_TITLE) }
    var author by remember { mutableStateOf(book.author ?: NO_BOOK_AUTHOR) }

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
                onUpdateTitle = { newTitle ->
                    title = newTitle
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            AuthorTextField(
                author = author,
                onUpdateAuthor = { newAuthor ->
                    author = newAuthor
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
                        if (title.isEmpty()) {
                            onEmptyBookField(TITLE_FIELD)
                        } else if (author.isEmpty()) {
                            onEmptyBookField(AUTHOR_FIELD)
                        } else {
                            val bookUpdates = mutableMapOf<String, String>()
                            if (book.title != title) {
                                bookUpdates[TITLE_FIELD] = title
                            }
                            if (book.author != author) {
                                bookUpdates[AUTHOR_FIELD] = author
                            }
                            if (bookUpdates.isNotEmpty()) {
                                bookUpdates[ID_FIELD] = book.id
                                onUpdateBook(bookUpdates)
                            } else {
                                onNoUpdates()
                            }
                        }
                    },
                    resourceId = R.string.update_button
                )
            }
        }
    }
}