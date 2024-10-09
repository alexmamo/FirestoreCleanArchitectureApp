package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.domain.repository.AddBookResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BookResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.DeleteBookResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.UpdateBookResponse
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repo: BooksRepository
): ViewModel() {
    var booksResponse by mutableStateOf<BooksResponse>(Loading)
        private set
    var addBookResponse by mutableStateOf<AddBookResponse>(Success(null))
        private set
    var bookResponse by mutableStateOf<BookResponse>(Success(null))
        private set
    var updateBookResponse by mutableStateOf<UpdateBookResponse>(Success(null))
        private set
    var deleteBookResponse by mutableStateOf<DeleteBookResponse>(Success(null))
        private set

    init {
        getBooks()
    }

    private fun getBooks() = viewModelScope.launch {
        repo.getBooks().collect { response ->
            booksResponse = response
        }
    }

    fun addBook(book: Book) = viewModelScope.launch {
        addBookResponse = Loading
        addBookResponse = repo.addBook(book)
    }

    fun getBook(id: String) = viewModelScope.launch {
        bookResponse = Loading
        bookResponse = repo.getBook(id)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        updateBookResponse = Loading
        updateBookResponse = repo.updateBook(book)
    }

    fun deleteBook(id: String) = viewModelScope.launch {
        deleteBookResponse = Loading
        deleteBookResponse = repo.deleteBook(id)
    }
}