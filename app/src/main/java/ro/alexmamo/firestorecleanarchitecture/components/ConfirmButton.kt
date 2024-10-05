package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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