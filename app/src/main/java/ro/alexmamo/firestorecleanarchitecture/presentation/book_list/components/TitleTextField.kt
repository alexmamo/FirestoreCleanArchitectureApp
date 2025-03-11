package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import ro.alexmamo.firestorecleanarchitecture.R

@Composable
fun TitleTextField(
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = title,
        onValueChange = onTitleChange,
        placeholder = {
            Text(
                text = stringResource(
                    id = R.string.book_title
                )
            )
        },
        singleLine = true
    )
}