package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ro.alexmamo.firestorecleanarchitecture.core.AUTHOR_FIELD
import ro.alexmamo.firestorecleanarchitecture.core.ID_FIELD
import ro.alexmamo.firestorecleanarchitecture.core.TITLE_FIELD
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.BookField

const val NON_EXISTENT_BOOK_ID = "NO_ID"

@Composable
fun BookListContent(
    innerPadding: PaddingValues,
    bookList: List<Book>,
    onUpdateBook: (Map<String, String>) -> Unit,
    onInvalidBookField: (BookField) -> Unit,
    onDeleteBook: (String) -> Unit
) {
    var editBookId by remember { mutableStateOf(NON_EXISTENT_BOOK_ID) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        items(
            items = bookList,
            key = { book ->
                book.id
            }
        ) { book ->
            if (editBookId != book.id) {
                BookCard(
                    book = book,
                    onEditBook = {
                        editBookId = book.id
                    },
                    onDeleteBook = {
                        onDeleteBook(book.id)
                        editBookId = NON_EXISTENT_BOOK_ID
                    }
                )
            } else {
                EditableBookCard(
                    book = book,
                    onUpdateBook = { title, author ->
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
                        }
                        editBookId = NON_EXISTENT_BOOK_ID
                    },
                    onInvalidBookField = onInvalidBookField,
                    onCancel = {
                        editBookId = NON_EXISTENT_BOOK_ID
                    }
                )
            }
        }
    }
}