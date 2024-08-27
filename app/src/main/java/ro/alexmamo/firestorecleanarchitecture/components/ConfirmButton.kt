package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmButton(
    confirmText: String,
    confirmAction: () -> Unit
) {
    TextButton(
        onClick = confirmAction
    ) {
        Text(
            text = confirmText
        )
    }
}