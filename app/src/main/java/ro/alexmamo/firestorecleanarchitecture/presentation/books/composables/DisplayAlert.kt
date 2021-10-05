package ro.alexmamo.firestorecleanarchitecture.presentation.books.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksViewModel
import ro.alexmamo.firestorecleanarchitecture.utils.Constants.ADD
import ro.alexmamo.firestorecleanarchitecture.utils.Constants.ADD_BOOK
import ro.alexmamo.firestorecleanarchitecture.utils.Constants.AUTHOR
import ro.alexmamo.firestorecleanarchitecture.utils.Constants.BOOK_TITLE
import ro.alexmamo.firestorecleanarchitecture.utils.Constants.DISMISS

@Composable
@InternalCoroutinesApi
fun Alert(
    viewModel: BooksViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()

    if (viewModel.openDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.openDialogState.value = false
            },
            title = {
                Text(text = ADD_BOOK)
            },
            text = {
                Column {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text(BOOK_TITLE) },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    DisposableEffect(Unit) {
                        focusRequester.requestFocus()
                        onDispose { }
                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = author,
                        onValueChange = { author = it },
                        placeholder = { Text(AUTHOR) }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState.value = false
                        viewModel.addBook(title, author)
                    }
                ) {
                    Text(ADD)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialogState.value = false
                    }
                ) {
                    Text(DISMISS)
                }
            }
        )
    }
}