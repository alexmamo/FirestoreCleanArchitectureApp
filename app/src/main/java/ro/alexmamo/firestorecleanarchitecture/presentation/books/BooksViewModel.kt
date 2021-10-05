package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.domain.operations.Actions
import javax.inject.Inject

@HiltViewModel
@InternalCoroutinesApi
class BooksViewModel @Inject constructor(
    private val actions: Actions
): ViewModel() {
    private val _booksState = mutableStateOf<Response<List<Book>>>(Response.Loading)
    val booksState: State<Response<List<Book>>> = _booksState

    private val _isBookAddedState = mutableStateOf<Response<Void?>>(Success(null))
    val isBookAddedState: State<Response<Void?>> = _isBookAddedState
    var openDialogState = mutableStateOf(false)

    private val _isBookDeletedState = mutableStateOf<Response<Void?>>(Success(null))
    val isBookDeletedState: State<Response<Void?>> = _isBookDeletedState

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            actions.getBooks().collect { response ->
                _booksState.value = response
            }
        }
    }

    fun addBook(title: String, author: String) {
        viewModelScope.launch {
            actions.addBook(title, author).collect { response ->
                _isBookAddedState.value = response
            }
        }
    }

    fun deleteBook(bookId: String) {
        viewModelScope.launch {
            actions.deleteBook(bookId).collect { response ->
                _isBookDeletedState.value = response
            }
        }
    }
}