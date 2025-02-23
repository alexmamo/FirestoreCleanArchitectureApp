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

@Composable
fun BookListScreen(
    viewModel: BookListViewModel = viewModel()
) {
    val context = LocalContext.current
    val resources = context.resources
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var openAddBookDialog by remember { mutableStateOf(false) }
    val bookListResponse by viewModel.bookListState.collectAsStateWithLifecycle()
    val addBookResponse by viewModel.addBookState.collectAsStateWithLifecycle()
    val updateBookResponse by viewModel.updateBookState.collectAsStateWithLifecycle()
    val deleteBookResponse by viewModel.deleteBookState.collectAsStateWithLifecycle()

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
        when(val bookListResponse = bookListResponse) {
            is Response.Idle -> {}
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
                        onNoBookUpdates = {
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

    when(val addBookResponse = addBookResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showSnackbarMessage(
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                message = resources.getString(R.string.book_action_message, BookAction.ADDED)
            )
            viewModel.resetAddBookState()
        }
        is Response.Failure -> addBookResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }

    when(val updateBookResponse = updateBookResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showSnackbarMessage(
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                message = resources.getString(R.string.book_action_message, BookAction.UPDATED)
            )
            viewModel.resetUpdateBookState()
        }
        is Response.Failure -> updateBookResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }

    when(val deleteBookResponse = deleteBookResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showSnackbarMessage(
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState,
                message = resources.getString(R.string.book_action_message, BookAction.DELETED)
            )
            viewModel.resetDeleteBookState()
        }
        is Response.Failure -> deleteBookResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}

enum class BookAction() {
    ADDED,
    UPDATED,
    DELETED
}