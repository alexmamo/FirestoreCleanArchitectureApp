package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun AuthorText(
    authorText: String
) {
    Text(
        text = "by $authorText",
        color = Color.DarkGray,
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
    )
}