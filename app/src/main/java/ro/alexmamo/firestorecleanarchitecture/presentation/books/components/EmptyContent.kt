package ro.alexmamo.firestorecleanarchitecture.presentation.books.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import ro.alexmamo.firestorecleanarchitecture.core.Constants.EMPTY_BOOK_LIST_MESSAGE

@Composable
fun EmptyContent(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = EMPTY_BOOK_LIST_MESSAGE,
            fontSize = 18.sp
        )
    }
}