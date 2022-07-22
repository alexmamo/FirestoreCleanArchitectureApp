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
        floatingActionButton = {
            AddBookFloatingActionButton(
                openDialog = {
                    viewModel.openDialog()
                }
            )
        },
        content = { padding ->
            when(val booksResponse = viewModel.booksState.value) {
                is Loading -> ProgressBar()
                is Success -> BooksContent(
                    padding = padding,
                    products = booksResponse.data,
                    deleteBook = { bookId ->
                        viewModel.deleteBook(bookId)
                    }
                )
                is Error -> printMessage(booksResponse.message)
            }
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

    when(val additionResponse = viewModel.isBookAddedState.value) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Error -> printMessage(additionResponse.message)
    }

    when(val deletionResponse = viewModel.isBookDeletedState.value) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Error -> printMessage(deletionResponse.message)
    }
}