package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ro.alexmamo.firestorecleanarchitecture.components.ProgressBar
import ro.alexmamo.firestorecleanarchitecture.core.printError
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Failure
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel

@Composable
fun DeleteBook(
    viewModel: BooksViewModel = viewModel()
) {
    when(val deleteBookResponse = viewModel.deleteBookResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> printError(deleteBookResponse.e)
    }
}