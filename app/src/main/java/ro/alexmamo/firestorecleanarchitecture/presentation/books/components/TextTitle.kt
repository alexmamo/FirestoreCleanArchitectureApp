package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(
    bookTitle: String
) {
    Text(
        text = bookTitle,
        color = Color.DarkGray,
        fontSize = 25.sp
    )
}