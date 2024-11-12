package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import ro.alexmamo.firestorecleanarchitecture.R

@Composable
fun TitleTextField(
    title: String,
    onUpdateTitle: (String) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(
        text = title,
        selection = TextRange(title.length)
    )) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = title,
        onValueChange = { newTitle ->
            title = newTitle
            onUpdateTitle(newTitle.text)
        },
        placeholder = {
            Text(
                text = stringResource(
                    id = R.string.book_title
                )
            )
        }
    )
}