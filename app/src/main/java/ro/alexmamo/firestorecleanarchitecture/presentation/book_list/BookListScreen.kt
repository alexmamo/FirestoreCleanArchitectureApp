package ro.alexmamo.firestorecleanarchitecture.presentation.book_list

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
import ro.alexmamo.firestorecleanarchitecture.core.showToastMessage
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
                        onEmptyTitleUpdate = {
                            showToastMessage(context, R.string.empty_title_message)
                        },
                        onEmptyAuthorUpdate = {
                            showToastMessage(context, R.string.empty_author_message)
                        },
                        onNoUpdates = {
                            showToastMessage(context, R.string.no_updates_message)
                        },
                        onDeleteBook = { id ->
                            viewModel.deleteBook(id)
                            deletingBook = true
                        }
                    )
                }
            }
            is Response.Failure -> printError(bookListResponse.e)
        }
    }

    if (openAddBookDialog) {
        AddBookAlertDialog(
            onEmptyTitleInsert = {
                showToastMessage(context, R.string.empty_title_message)
            },
            onEmptyAuthorInsert = {
                showToastMessage(context, R.string.empty_author_message)
            },
            onAddBook = { book ->
                viewModel.addBook(book)
                addingBook = true
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