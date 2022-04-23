package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import ro.alexmamo.firestorecleanarchitecture.core.Utils.Companion.printError
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookFloatingActionButton
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.AddBookAlertDialog
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BookCard
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.ProgressBar

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            AddBookFloatingActionButton()
        }
    ) {
        it.calculateTopPadding()
        if(viewModel.openDialogState.value) {
            AddBookAlertDialog()
        }
        when(val booksResponse = viewModel.booksState.value) {
            is Loading -> ProgressBar()
            is Success -> Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    items(
                        items = booksResponse.data
                    ) { book ->
                        BookCard(
                            book = book
                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when(val additionResponse = viewModel.isBookAddedState.value) {
                        is Loading -> CircularProgressIndicator()
                        is Success -> Unit
                        is Error -> printError(additionResponse.message)
                    }
                }
            }
            is Error -> printError(booksResponse.message)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(val deletionResponse = viewModel.isBookDeletedState.value) {
                is Loading -> CircularProgressIndicator()
                is Success -> Unit
                is Error -> printError(deletionResponse.message)
            }
        }
    }
}