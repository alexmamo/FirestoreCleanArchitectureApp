package ro.alexmamo.firestorecleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response

typealias Books = List<Book>
typealias BooksResponse = Response<Books>
typealias AddBookResponse = Response<Boolean>
typealias GetBookResponse = Response<Book>
typealias EditBookResponse = Response<Boolean>
typealias DeleteBookResponse = Response<Boolean>

interface BooksRepository {
    fun getBooks(): Flow<BooksResponse>

    suspend fun addBook(book: MutableMap<String, Any>): AddBookResponse

    suspend fun getBookById(bookId: String): GetBookResponse

    suspend fun editBook(bookId: String, book: MutableMap<String, Any>): EditBookResponse

    suspend fun deleteBookById(bookId: String): DeleteBookResponse
}