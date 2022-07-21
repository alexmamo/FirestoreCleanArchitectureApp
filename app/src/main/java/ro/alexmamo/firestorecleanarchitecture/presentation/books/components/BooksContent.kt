package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

@Composable
fun BooksContent(
    padding: PaddingValues,
    products: List<Book>,
    deleteBook: (bookId: String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn {
            items(
                items = products
            ) { book ->
                BookCard(
                    book = book,
                    deleteBook = {
                        book.id?.let { bookId ->
                            deleteBook(bookId)
                        }
                    }
                )
            }
        }
    }
}