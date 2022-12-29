package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel

@Composable
fun AddBook(
    viewModel: BooksViewModel = hiltViewModel()
) {
    when(val addBookResponse = viewModel.addBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> print(addBookResponse.e)
    }
}