package ro.alexmamo.firestorecleanarchitecture.presentation.book_list

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.R
import ro.alexmamo.firestorecleanarchitecture.components.LoadingIndicator
import ro.alexmamo.firestorecleanarchitecture.core.logMessage
import ro.alexmamo.firestorecleanarchitecture.core.showSnackbarMessage
import ro.alexmamo.firestorecleanarchitecture.core.showToastMessage
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.BookListContent
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.EmptyBookListContent
import ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components.TopBar

const val ADDED_STATE = "added"
const val UPDATED_STATE = "updated"
const val DELETED_STATE = "deleted"

@Composable
fun BookListScreen(
    viewModel: BookListViewModel = viewModel()
) {
    val context = LocalContext.current
    val resources = context.resources
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var openAddBookDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            AddBookFloatingActionButton(
                onAddBookFloatingActionButtonClick = {
                    openAddBookDialog = true
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        when(val bookListResponse = viewModel.bookListResponse.collectAsStateWithLifecycle().value) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> bookListResponse.data?.let { bookList ->
                if (bookList.isEmpty()) {
                    EmptyBookListContent(
                        innerPadding = innerPadding
                    )
                } else {
                    BookListContent(
                        innerPadding = innerPadding,
                        bookList = bookList,
                        onUpdateBook = { book ->
                            viewModel.updateBook(book)
                        },
                        onEmptyBookField = { bookField ->
                            showSnackbarMessage(
                                coroutineScope = coroutineScope,
                                snackbarHostState = snackbarHostState,
                                message = resources.getString(R.string.empty_book_field_message, bookField)
                            )
                        },
                        onDeleteBook = { bookId ->
                            viewModel.deleteBook(bookId)
                        },
                        onNoUpdates = {
                            showSnackbarMessage(
                                coroutineScope = coroutineScope,
                                snackbarHostState = snackbarHostState,
                                message = resources.getString(R.string.no_book_updates_message)
                            )
                        }
                    )
                }
            }
            is Response.Failure -> bookListResponse.e?.message?.let { errorMessage ->
                LaunchedEffect(errorMessage) {
                    logMessage(errorMessage)
                    showToastMessage(context, errorMessage)
                }
            }
        }
    }

    if (openAddBookDialog) {
        AddBookAlertDialog(
            onAddBook = { book ->
                viewModel.addBook(book)
            },
            onEmptyBookField = { emptyField ->
                showSnackbarMessage(
                    coroutineScope = coroutineScope,
                    snackbarHostState = snackbarHostState,
                    message = resources.getString(R.string.empty_book_field_message, emptyField)
                )
            },
            onAddBookDialogCancel = {
                openAddBookDialog = false
            }
        )
    }

    when(val addBookResponse = viewModel.addBookResponse.collectAsStateWithLifecycle().value) {
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showSnackbarMessage(
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                message = resources.getString(R.string.book_state_message, ADDED_STATE)
            )
            viewModel.resetAddBookState()
        }
        is Response.Failure -> addBookResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
        null -> {}
    }

    when(val updateBookResponse = viewModel.updateBookResponse.collectAsStateWithLifecycle().value) {
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showSnackbarMessage(
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                message = resources.getString(R.string.book_state_message, UPDATED_STATE)
            )
            viewModel.resetUpdateBookState()
        }
        is Response.Failure -> updateBookResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
        null -> {}
    }

    when(val deleteBookResponse = viewModel.deleteBookResponse.collectAsStateWithLifecycle().value) {
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showSnackbarMessage(
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                message = resources.getString(R.string.book_state_message, DELETED_STATE)
            )
            viewModel.resetDeleteBookState()
        }
        is Response.Failure -> deleteBookResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
        null -> {}
    }
}