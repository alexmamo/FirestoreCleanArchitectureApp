package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import ro.alexmamo.firestorecleanarchitecture.core.Constants.DELETE_ITEM

@Composable
fun DeleteIcon(
    onDeleteIconClick: () -> Unit
) {
    IconButton(
        onClick = onDeleteIconClick
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = DELETE_ITEM,
        )
    }
}