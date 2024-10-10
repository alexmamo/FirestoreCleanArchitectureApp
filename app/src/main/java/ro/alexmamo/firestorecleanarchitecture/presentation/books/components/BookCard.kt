package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ro.alexmamo.firestorecleanarchitecture.components.DeleteIcon
import ro.alexmamo.firestorecleanarchitecture.components.EditIcon
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

@Composable
fun BookCard(
    book: Book,
    onEditIconClick: () -> Unit,
    onDeleteIconClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                BookTitleText(
                    bookTitle = book.title.orEmpty()
                )
                AuthorText(
                    authorText = book.author.orEmpty()
                )
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            EditIcon(
                onEditIconClick = onEditIconClick
            )
            DeleteIcon(
                onDeleteIconClick = onDeleteIconClick
            )
        }
    }
}