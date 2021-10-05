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
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel
import ro.alexmamo.firestorecleanarchitecture.utils.Constants.DELETE_BOOK

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun BookCard(
    book: Book,
    viewModel: BooksViewModel = hiltViewModel()
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
                    book.id?.let { bookId ->
                        viewModel.deleteBook(bookId)
                    }
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