package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EDIT_ITEM

@Composable
fun EditIcon(
    onEditIconClick: () -> Unit
) {
    IconButton(
        onClick = onEditIconClick
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = EDIT_ITEM,
        )
    }
}