package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firestorecleanarchitecture.components.TopBar
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBook
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.Books
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BooksContent
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.DeleteBook
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.EditBook
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.EditBookAlertDialog

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    var openAddBookDialog by remember { mutableStateOf(false) }
    var openEditBookDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar()
        },
        content = { padding ->
            Books(
                booksContent = { books ->
                    BooksContent(
                        padding = padding,
                        books = books,
                        editBook = { id ->
                            openEditBookDialog = true
                            viewModel.getBook(id)
                        },
                        deleteBook = { id ->
                            viewModel.deleteBook(id)
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            AddBookFloatingActionButton(
                openDialog = {
                    openAddBookDialog = true
                }
            )
        }
    )
    if (openAddBookDialog) {
        AddBookAlertDialog(
            addBook = { book ->
                viewModel.addBook(book)
            },
            closeDialog = {
                openAddBookDialog = false
            }
        )
    }
    if (openEditBookDialog) {
        EditBookAlertDialog(
            editBook = { id, book ->
                viewModel.editBook(id, book)
            },
            closeDialog = {
                openEditBookDialog = false
            }
        )
    }
    AddBook()
    EditBook()
    DeleteBook()
}