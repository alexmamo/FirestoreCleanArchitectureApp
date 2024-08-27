package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ro.alexmamo.firestorecleanarchitecture.domain.repository.Books

@Composable
fun BooksContent(
    padding: PaddingValues,
    books: Books,
    updateBook: (id: String) -> Unit,
    deleteBook: (id: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding)
    ) {
        items(
            items = books
        ) { book ->
            BookCard(
                book = book,
                updateBook = {
                    book.id?.let { id ->
                        updateBook(id)
                    }
                },
                deleteBook = {
                    book.id?.let { id ->
                        deleteBook(id)
                    }
                }
            )
        }
    }
}