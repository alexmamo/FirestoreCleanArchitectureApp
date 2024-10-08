package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.components.TopBar
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EMPTY_AUTHOR_MESSAGE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EMPTY_TITLE_MESSAGE
import ro.alexmamo.firestorecleanarchitecture.core.Constants.NO_UPDATES_MESSAGE
import ro.alexmamo.firestorecleanarchitecture.core.printError
import ro.alexmamo.firestorecleanarchitecture.core.toastMessage
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Failure
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BooksContent
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.EmptyContent
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.UpdateBookAlertDialog

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = viewModel()
) {
    val context = LocalContext.current
    var openAddBookDialog by remember { mutableStateOf(false) }
    var openUpdateBookDialog by remember { mutableStateOf(false) }
    var book by remember { mutableStateOf(Book()) }

    Scaffold(
        topBar = {
            TopBar()
        },
        content = { padding ->
            when(val booksResponse = viewModel.booksResponse) {
                is Loading -> ProgressBar()
                is Success -> booksResponse.data?.let { books ->
                    if (books.isEmpty()) {
                        EmptyContent()
                    } else {
                        BooksContent(
                            padding = padding,
                            books = books,
                            updateBook = { bookToUpdate ->
                                book = bookToUpdate
                                openUpdateBookDialog = true
                            },
                            deleteBook = { id ->
                                viewModel.deleteBook(id)
                            }
                        )
                    }
                }
                is Failure -> printError(booksResponse.e)
            }
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
            book = book,
            showEmptyTitleMessage = {
                toastMessage(context, EMPTY_TITLE_MESSAGE)
            },
            showEmptyAuthorMessage = {
                toastMessage(context, EMPTY_AUTHOR_MESSAGE)
            },
            updateBook = { book ->
                viewModel.updateBook(book)
            },
            showNoUpdatesMessage = {
                toastMessage(context, NO_UPDATES_MESSAGE)
            },
            closeDialog = {
                openUpdateBookDialog = false
            }
        )
    }
    when(val addBookResponse = viewModel.addBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> printError(addBookResponse.e)
    }
    when(val updateBookResponse = viewModel.updateBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> printError(updateBookResponse.e)
    }
    when(val deleteBookResponse = viewModel.deleteBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> printError(deleteBookResponse.e)
    }
}