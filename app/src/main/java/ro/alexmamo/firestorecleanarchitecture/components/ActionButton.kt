package ro.alexmamo.firestorecleanarchitecture.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun ActionButton(
    onActionButtonClick: () -> Unit,
    resourceId: Int
) {
    Button(
        onClick = onActionButtonClick
    ) {
        Text(
            text = stringResource(
                id = resourceId
            )
        )
    }
}