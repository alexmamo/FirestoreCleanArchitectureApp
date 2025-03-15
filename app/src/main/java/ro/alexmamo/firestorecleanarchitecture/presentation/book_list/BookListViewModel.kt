package ro.alexmamo.firestorecleanarchitecture.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
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
    private val _bookListState = MutableStateFlow<BookListResponse>(Response.Loading)
    val bookListState: StateFlow<BookListResponse> = _bookListState.asStateFlow()

    private val _addBookState = MutableStateFlow<AddBookResponse>(Response.Idle)
    val addBookState: StateFlow<AddBookResponse> = _addBookState.asStateFlow()

    private val _updateBookState = MutableStateFlow<UpdateBookResponse>(Response.Idle)
    val updateBookState: StateFlow<UpdateBookResponse> = _updateBookState.asStateFlow()

    private val _deleteBookState = MutableStateFlow<DeleteBookResponse>(Response.Idle)
    val deleteBookState: StateFlow<DeleteBookResponse> = _deleteBookState.asStateFlow()

    init {
        getBookList()
    }

    private fun getBookList() = viewModelScope.launch {
        repo.getBookList().collect { response ->
            _bookListState.value = response
        }
    }

    fun addBook(book: Map<String, String>) = viewModelScope.launch {
        _addBookState.value = Response.Loading
        _addBookState.value = repo.addBook(book)
    }

    fun resetAddBookState() {
        _addBookState.value = Response.Idle
    }

    fun updateBook(bookUpdates: Map<String, String>) = viewModelScope.launch {
        _updateBookState.value = Response.Loading
        _updateBookState.value = repo.updateBook(bookUpdates)
    }

    fun resetUpdateBookState() {
        _updateBookState.value = Response.Idle
    }

    fun deleteBook(bookId: String) = viewModelScope.launch {
        _deleteBookState.value = Response.Loading
        _deleteBookState.value = repo.deleteBook(bookId)
    }

    fun resetDeleteBookState() {
        _deleteBookState.value = Response.Idle
    }
}