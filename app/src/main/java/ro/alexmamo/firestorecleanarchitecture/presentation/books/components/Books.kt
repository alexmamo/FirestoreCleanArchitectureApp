package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.core.Utils.Companion.print
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*
import ro.alexmamo.firestorecleanarchitecture.domain.repository.Books
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel

@Composable
fun Books(
    viewModel: BooksViewModel = hiltViewModel(),
    booksContent: @Composable (books: Books) -> Unit
) {
    when(val booksResponse = viewModel.booksResponse) {
        is Loading -> ProgressBar()
        is Success -> booksContent(booksResponse.data)
        is Failure -> print(booksResponse.e)
    }
}