package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firestorecleanarchitecture.components.TopBar
import ro.alexmamo.firestorecleanarchitecture.presentation.books.components.*

@Composable
fun BooksScreen(
    viewModel: BooksViewModel = hiltViewModel()
) {
    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar()
        },
        content = { padding ->
            Books(
                booksContent = { books ->
                    BooksContent(
                        padding = padding,
                        books = books,
                        deleteBook = { bookId ->
                            viewModel.deleteBook(bookId)
                        }
                    )
                    if (openDialog) {
                        AddBookAlertDialog(
                            closeDialog = {
                                openDialog = false
                            },
                            addBook = { title, author ->
                                viewModel.addBook(title, author)
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AddBookFloatingActionButton(
                openDialog = {
                    openDialog = true
                }
            )
        }
    )
    AddBook()
    DeleteBook()
}