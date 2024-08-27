package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.core.printError
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Failure
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.domain.repository.Books
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel

@Composable
fun Books(
    viewModel: BooksViewModel = viewModel(),
    booksContent: @Composable (books: Books) -> Unit
) {
    when(val booksResponse = viewModel.booksResponse) {
        is Loading -> ProgressBar()
        is Success -> booksResponse.data?.let { books ->
            booksContent(books)
        }
        is Failure -> printError(booksResponse.e)
    }
}