package ro.alexmamo.firestorecleanarchitecture.presentation.book_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.repository.AddBookResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BookListRepository
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BookListResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.DeleteBookResponse
import ro.alexmamo.firestorecleanarchitecture.domain.repository.UpdateBookResponse
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repo: BookListRepository
): ViewModel() {
    var bookListResponse by mutableStateOf<BookListResponse>(Loading)
        private set
    var addBookResponse by mutableStateOf<AddBookResponse>(Loading)
        private set
    var updateBookResponse by mutableStateOf<UpdateBookResponse>(Loading)
        private set
    var deleteBookResponse by mutableStateOf<DeleteBookResponse>(Loading)
        private set

    init {
        getBookList()
    }

    private fun getBookList() = viewModelScope.launch {
        repo.getBookList().collect { response ->
            bookListResponse = response
        }
    }

    fun addBook(book: Book) = viewModelScope.launch {
        addBookResponse = repo.addBook(book)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        updateBookResponse = repo.updateBook(book)
    }

    fun deleteBook(bookId: String) = viewModelScope.launch {
        deleteBookResponse = repo.deleteBook(bookId)
    }
}