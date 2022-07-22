package ro.alexmamo.firestorecleanarchitecture.presentation.books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Loading
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.domain.use_case.UseCases
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    var booksState by mutableStateOf<Response<List<Book>>>(Loading)
        private set
    var isBookAddedState by mutableStateOf<Response<Void?>>(Success(null))
        private set
    var isBookDeletedState by mutableStateOf<Response<Void?>>(Success(null))
        private set
    var openDialog by mutableStateOf(false)

    init {
        getBooks()
    }

    private fun getBooks() = viewModelScope.launch {
        useCases.getBooks().collect { response ->
            booksState = response
        }
    }

    fun addBook(title: String, author: String) = viewModelScope.launch {
        useCases.addBook(title, author).collect { response ->
            isBookAddedState = response
        }
    }

    fun deleteBook(bookId: String) = viewModelScope.launch {
        useCases.deleteBook(bookId).collect { response ->
            isBookDeletedState = response
        }
    }

    fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }
}