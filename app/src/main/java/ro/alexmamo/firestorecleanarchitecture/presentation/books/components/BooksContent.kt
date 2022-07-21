package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.core.Utils.Companion.printError
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*

@Composable
fun BooksContent(
    padding: PaddingValues,
    booksResponse: Response<List<Book>>,
    deleteBook: (bookId: String) -> Unit
) {
    when(booksResponse) {
        is Loading -> ProgressBar()
        is Success -> LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            items(
                items = booksResponse.data
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
        is Error -> printError(booksResponse.message)
    }
}