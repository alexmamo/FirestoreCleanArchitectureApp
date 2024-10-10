package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.repository.Books

@Composable
fun BooksContent(
    padding: PaddingValues,
    books: Books,
    updateBook: (book: Book) -> Unit,
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
                onEditIconClick = {
                    updateBook(book)
                },
                onDeleteIconClick = {
                    book.id?.let { id ->
                        deleteBook(id)
                    }
                }
            )
        }
    }
}