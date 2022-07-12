package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.domain.use_case.UseCases
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    private val _booksState = mutableStateOf<Response<List<Book>>>(Success(emptyList()))
    val booksState: State<Response<List<Book>>> = _booksState

    private val _isBookAddedState = mutableStateOf<Response<Void?>>(Success(null))
    val isBookAddedState: State<Response<Void?>> = _isBookAddedState

    private val _isBookDeletedState = mutableStateOf<Response<Void?>>(Success(null))
    val isBookDeletedState: State<Response<Void?>> = _isBookDeletedState

    var isDialogOpen by mutableStateOf(false)

    init {
        getBooks()
    }

    private fun getBooks() = viewModelScope.launch {
        useCases.getBooks().collect { response ->
            _booksState.value = response
        }
    }

    fun addBook(title: String, author: String) = viewModelScope.launch {
        useCases.addBook(title, author).collect { response ->
            _isBookAddedState.value = response
        }
    }

    fun deleteBook(bookId: String) = viewModelScope.launch {
        useCases.deleteBook(bookId).collect { response ->
            _isBookDeletedState.value = response
        }
    }
}