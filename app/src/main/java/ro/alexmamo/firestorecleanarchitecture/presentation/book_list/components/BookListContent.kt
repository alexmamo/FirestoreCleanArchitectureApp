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
import ro.alexmamo.firestorecleanarchitecture.core.EMPTY_STRING
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book

const val NON_EXISTENT_BOOK_ID = "NO_ID"

@Composable
fun BookListContent(
    innerPadding: PaddingValues,
    bookList: List<Book>,
    onUpdateBook: (Book) -> Unit,
    onEmptyTitleUpdate: () -> Unit,
    onEmptyAuthorUpdate: () -> Unit,
    onNoUpdates: () -> Unit,
    onDeleteBook: (String) -> Unit
) {
    var editBookId by remember { mutableStateOf(NON_EXISTENT_BOOK_ID) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        items(
            items = bookList,
            key = { book ->
                book.id.orEmpty()
            }
        ) { book ->
            if (editBookId != book.id) {
                BookCard(
                    book = book,
                    onEditBook = {
                        editBookId = book.id.orEmpty()
                    },
                    onDeleteBook = {
                        onDeleteBook(book.id.orEmpty())
                        editBookId = NON_EXISTENT_BOOK_ID
                    }
                )
            } else {
                EditableBookCard(
                    book = book,
                    onUpdateBook = { updatedBook ->
                        updatedBook.apply {
                            if (title.equals(EMPTY_STRING)) {
                                onEmptyTitleUpdate()
                            } else if (author.equals(EMPTY_STRING)) {
                                onEmptyAuthorUpdate()
                            } else {
                                if (updatedBook != book) {
                                    onUpdateBook(updatedBook)
                                } else {
                                    onNoUpdates()
                                }
                                editBookId = NON_EXISTENT_BOOK_ID
                            }
                        }
                    },
                    onCancel = {
                        editBookId = NON_EXISTENT_BOOK_ID
                    }
                )
            }
        }
    }
}