package ro.alexmamo.firestorecleanarchitecture.presentation.book_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ro.alexmamo.firestorecleanarchitecture.R

@Composable
fun EmptyBookListContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = stringResource(
                id = R.string.empty_book_list_text
            ),
            fontSize = 18.sp
        )
    }
}