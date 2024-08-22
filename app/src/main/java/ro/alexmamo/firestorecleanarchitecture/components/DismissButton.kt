package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DismissButton(
    dismissText: String,
    closeDialog: () -> Unit,
) {
    TextButton(
        onClick = closeDialog
    ) {
        Text(
            text = dismissText
        )
    }
}