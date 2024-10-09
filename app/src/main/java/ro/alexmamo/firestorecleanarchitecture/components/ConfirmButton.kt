package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmButton(
    confirmText: String,
    onConfirmButtonClick: () -> Unit
) {
    TextButton(
        onClick = onConfirmButtonClick
    ) {
        Text(
            text = confirmText
        )
    }
}