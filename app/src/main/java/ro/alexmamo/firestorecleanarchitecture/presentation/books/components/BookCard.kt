package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.alexmamo.firestorecleanarchitecture.core.Constants.DELETE_BOOK
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

@Composable
fun BookCard(
    book: Book,
    deleteBook: () -> Unit,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
            ){
                book.title?.let { title ->
                    Text(
                        text = title,
                        color = Color.DarkGray,
                        fontSize = 25.sp
                    )
                }
                book.author?.let { author ->
                    Text(
                        text = "by $author",
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            IconButton(
                onClick = {
                    deleteBook()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = DELETE_BOOK,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}