package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.*

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
            BooksContent(
                padding = padding,
                booksResponse = viewModel.booksState.value,
                deleteBook = { bookId ->
                    viewModel.deleteBook(bookId)
                }
            )
        }
    )
    if(viewModel.openDialog) {
        AddBookAlertDialog(
            addBook = { title, author ->
                viewModel.addBook(title, author)
            },
            closeDialog = {
                viewModel.closeDialog()
            }
        )
    }
    BookAddition(
        additionResponse = viewModel.isBookAddedState.value
    )
    BookDeletion(
        deletionResponse = viewModel.isBookDeletedState.value
    )
}