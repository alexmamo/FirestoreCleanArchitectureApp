package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.core.Utils.Companion.printMessage
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BooksContent

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    Scaffold(
        content = { padding ->
            when(val booksResponse = viewModel.booksResponse) {
                is Loading -> ProgressBar()
                is Success -> {
                    BooksContent(
                        padding = padding,
                        books = booksResponse.data,
                        deleteBook = { bookId ->
                            viewModel.deleteBook(bookId)
                        }
                    )
                    AddBookAlertDialog(
                        openDialog = viewModel.openDialog,
                        closeDialog = {
                            viewModel.closeDialog()
                        },
                        addBook = { title, author ->
                            viewModel.addBook(title, author)
                        }
                    )
                }
                is Error -> printMessage(booksResponse.message)
            }
        },
        floatingActionButton = {
            AddBookFloatingActionButton(
                openDialog = {
                    viewModel.openDialog()
                }
            )
        }
    )

    when(val addBookResponse = viewModel.addBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Error -> printMessage(addBookResponse.message)
    }

    when(val deleteBookResponse = viewModel.deleteBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Error -> printMessage(deleteBookResponse.message)
    }
}