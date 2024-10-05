package ro.alexmamo.firestorecleanarchitecture.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ro.alexmamo.firestorecleanarchitecture.ui.theme.FirestoreCleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint
import ro.alexmamo.firestorecleanarchitecture.presentation.books.BooksScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirestoreCleanArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BooksScreen()
                }
            }
        }
    }
}