package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.alexmamo.firestorecleanarchitecture.R

@Composable
fun AddBookFloatingActionButton(
    onAddBookFloatingActionButtonClick: () -> Unit
) {
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.primary,
        onClick = onAddBookFloatingActionButtonClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(
                id = R.string.open_add_book_dialog
            )
        )
    }
}