package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material3.Scaffold
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
import ro.alexmamo.firestorecleanarchitecture.core.printError
import ro.alexmamo.firestorecleanarchitecture.core.toastMessage
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Failure
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BooksContent
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
            when(val booksResponse = viewModel.booksResponse) {
                is Loading -> ProgressBar()
                is Success -> booksResponse.data?.let { books ->
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
        when(val getBookResponse = viewModel.getBookResponse) {
            is Loading -> ProgressBar()
            is Success -> getBookResponse.data?.let { selectedBook ->
                UpdateBookAlertDialog(
                    selectedBook = selectedBook,
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
            is Failure -> printError(getBookResponse.e)
        }
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