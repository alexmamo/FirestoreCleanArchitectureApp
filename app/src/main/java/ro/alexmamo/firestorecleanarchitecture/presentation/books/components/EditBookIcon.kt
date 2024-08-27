package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EDIT_BOOK

@Composable
fun EditBookIcon(
    updateBook: () -> Unit
) {
    IconButton(
        onClick = updateBook
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = EDIT_BOOK,
        )
    }
}