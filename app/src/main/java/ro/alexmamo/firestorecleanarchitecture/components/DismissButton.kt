package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DismissButton(
    dismissText: String,
    onDismissButtonClick: () -> Unit,
) {
    TextButton(
        onClick = onDismissButtonClick
    ) {
        Text(
            text = dismissText
        )
    }
}