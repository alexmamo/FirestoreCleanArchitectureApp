package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.components.TopBar
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EMPTY_AUTHOR_MESSAGE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EMPTY_TITLE_MESSAGE
import ro.alexmamo.firestorecleanarchitecture.core.toastMessage
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBook
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.Books
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BooksContent
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.DeleteBook
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.UpdateBook
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.UpdateBookAlertDialog

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = viewModel()
) {
    val context = LocalContext.current
    var openAddBookDialog by remember { mutableStateOf(false) }
    var openUpdateBookDialog by remember { mutableStateOf(false) }

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
                        updateBook = { id ->
                            openUpdateBookDialog = true
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
            showEmptyTitleMessage = {
                toastMessage(context, EMPTY_TITLE_MESSAGE)
            },
            showEmptyAuthorMessage = {
                toastMessage(context, EMPTY_AUTHOR_MESSAGE)
            },
            addBook = { book ->
                viewModel.addBook(book)
            },
            closeDialog = {
                openAddBookDialog = false
            }
        )
    }
    if (openUpdateBookDialog) {
        UpdateBookAlertDialog(
            showEmptyTitleMessage = {
                toastMessage(context, EMPTY_TITLE_MESSAGE)
            },
            showEmptyAuthorMessage = {
                toastMessage(context, EMPTY_AUTHOR_MESSAGE)
            },
            updateBook = { id, book ->
                viewModel.updateBook(id, book)
            },
            closeDialog = {
                openUpdateBookDialog = false
            }
        )
    }
    AddBook()
    UpdateBook()
    DeleteBook()
}