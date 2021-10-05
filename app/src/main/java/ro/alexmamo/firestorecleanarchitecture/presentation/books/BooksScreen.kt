package ro.alexmamo.firestorecleanarchitecture.presentation.books

import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.Alert
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.BookCard
import ro.alexmamo.firestorecleanarchitecture.utils.Constants

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.openDialogState.value = true
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ADD_BOOK
                )
            }
        }
    ) {
        if(viewModel.openDialogState.value) {
            Alert()
        }
        when(val booksResponse = viewModel.booksState.value) {
            is Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
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
                        is Error -> Toast(additionResponse.message)
                    }
                }
            }
            is Error -> Toast(booksResponse.message)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(val deletionResponse = viewModel.isBookDeletedState.value) {
                is Loading -> CircularProgressIndicator()
                is Success -> Unit
                is Error -> Toast(deletionResponse.message)
            }
        }
    }
}

@Composable
fun Toast(message: String) {
    makeText(LocalContext.current, message, LENGTH_SHORT).show()
}