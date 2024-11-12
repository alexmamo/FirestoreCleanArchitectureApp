package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import ro.alexmamo.firestorecleanarchitecture.R

@Composable
fun AuthorTextField(
    author: String,
    onUpdateAuthor: (String) -> Unit
) {
    var author by remember { mutableStateOf(author) }

    TextField(
        value = author,
        onValueChange = { newAuthor ->
            author = newAuthor
            onUpdateAuthor(newAuthor)
        },
        placeholder = {
            Text(
                text = stringResource(
                    id = R.string.book_author
                )
            )
        }
    )
}