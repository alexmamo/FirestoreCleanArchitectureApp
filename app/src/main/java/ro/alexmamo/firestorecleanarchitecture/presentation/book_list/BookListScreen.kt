package ro.alexmamo.firestorecleanarchitecture.presentation.book_list

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.R
import ro.alexmamo.firestorecleanarchitecture.components.LoadingIndicator
import ro.alexmamo.firestorecleanarchitecture.core.printError
import ro.alexmamo.firestorecleanarchitecture.core.showMessage
import ro.alexmamo.firestorecleanarchitecture.domain.model.BookError
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.BookListContent
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.BookListTopBar
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.EmptyBookListContent

@Composable
fun BookListScreen(
    viewModel: BookListViewModel = viewModel()
) {
    val context = LocalContext.current
    var openAddBookDialog by remember { mutableStateOf(false) }
    var addingBook by remember { mutableStateOf(false) }
    var updatingBook by remember { mutableStateOf(false) }
    var deletingBook by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BookListTopBar()
        },
        floatingActionButton = {
            AddBookFloatingActionButton(
                onAddBookFloatingActionButtonClick = {
                    openAddBookDialog = true
                }
            )
        }
    ) { innerPadding ->
        when(val bookListResponse = viewModel.bookListResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> bookListResponse.data?.let { bookList ->
                if (bookList.isEmpty()) {
                    EmptyBookListContent()
                } else {
                    BookListContent(
                        innerPadding = innerPadding,
                        bookList = bookList,
                        onUpdateBook = { book ->
                            viewModel.updateBook(book)
                            updatingBook = true
                        },
                        onUpdateBookError = { bookError ->
                            showBookErrorMessage(context, bookError)
                        },
                        onDeleteBook = { bookId ->
                            viewModel.deleteBook(bookId)
                            deletingBook = true
                        },
                        onNoUpdates = {
                            showNoBookUpdatesMessage(context)
                        }
                    )
                }
            }
            is Response.Failure -> printError(bookListResponse.e)
        }
    }

    if (openAddBookDialog) {
        AddBookAlertDialog(
            onAddBook = { book ->
                viewModel.addBook(book)
                addingBook = true
            },
            onAddBookError = { bookError ->
                showBookErrorMessage(context, bookError)
            },
            onAddBookDialogCancel = {
                openAddBookDialog = false
            }
        )
    }

    if (addingBook) {
        when(val addBookResponse = viewModel.addBookResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> addingBook = false
            is Response.Failure -> printError(addBookResponse.e)
        }
    }

    if (updatingBook) {
        when(val updateBookResponse = viewModel.updateBookResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> updatingBook = false
            is Response.Failure -> printError(updateBookResponse.e)
        }
    }

    if (deletingBook) {
        when(val deleteBookResponse = viewModel.deleteBookResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> deletingBook = false
            is Response.Failure -> printError(deleteBookResponse.e)
        }
    }
}

fun showBookErrorMessage(
    context: Context,
    bookError: BookError
) {
    val resourceId = when(bookError) {
        BookError.EmptyBookTitle -> R.string.empty_book_title_message
        BookError.EmptyBookAuthor -> R.string.empty_book_author_message
    }
    showMessage(context, resourceId)
}

fun showNoBookUpdatesMessage(
    context: Context
) = showMessage(
    context = context,
    resourceId = R.string.no_book_updates_message
)