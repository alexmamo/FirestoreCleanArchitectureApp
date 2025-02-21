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
    private val _bookListResponse = MutableStateFlow<BookListResponse>(Response.Loading)
    val bookListResponse: StateFlow<BookListResponse> = _bookListResponse.asStateFlow()

    private val _addBookResponse = MutableStateFlow<AddBookResponse?>(null)
    val addBookResponse: StateFlow<AddBookResponse?> = _addBookResponse.asStateFlow()

    private val _updateBookResponse = MutableStateFlow<UpdateBookResponse?>(null)
    val updateBookResponse: StateFlow<UpdateBookResponse?> = _updateBookResponse.asStateFlow()

    private val _deleteBookResponse = MutableStateFlow<DeleteBookResponse?>(null)
    val deleteBookResponse: StateFlow<DeleteBookResponse?> = _deleteBookResponse.asStateFlow()

    init {
        getBookList()
    }

    private fun getBookList() = viewModelScope.launch {
        repo.getBookList().collect { response ->
            _bookListResponse.value = response
        }
    }

    fun addBook(book: Map<String, String>) = viewModelScope.launch {
        _addBookResponse.value = Response.Loading
        _addBookResponse.value = repo.addBook(book)
    }

    fun resetAddBookState() = _addBookResponse.value?.let {
        _addBookResponse.value = null
    }

    fun updateBook(bookUpdates: Map<String, String>) = viewModelScope.launch {
        _updateBookResponse.value = Response.Loading
        _updateBookResponse.value = repo.updateBook(bookUpdates)
    }

    fun resetUpdateBookState() = _updateBookResponse.value?.let {
        _updateBookResponse.value = null
    }

    fun deleteBook(bookId: String) = viewModelScope.launch {
        _deleteBookResponse.value = Response.Loading
        _deleteBookResponse.value = repo.deleteBook(bookId)
    }

    fun resetDeleteBookState() = _deleteBookResponse.value?.let {
        _deleteBookResponse.value = null
    }
}