package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import ro.alexmamo.firestorecleanarchitecture.core.Constants.ADD_BOOK

@Composable
fun AddBookFloatingActionButton(
    openDialog: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            openDialog()
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = ADD_BOOK
        )
    }
}