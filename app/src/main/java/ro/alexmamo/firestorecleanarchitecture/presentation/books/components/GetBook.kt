package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.core.printError
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Failure
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel

@Composable
fun GetBook(
    viewModel: BooksViewModel = viewModel(),
    bookContent: @Composable (book: Book) -> Unit
) {
    when(val getBookResponse = viewModel.getBookResponse) {
        is Loading -> ProgressBar()
        is Success -> getBookResponse.data?.let { book ->
            bookContent(book)
        }
        is Failure -> printError(getBookResponse.e)
    }
}