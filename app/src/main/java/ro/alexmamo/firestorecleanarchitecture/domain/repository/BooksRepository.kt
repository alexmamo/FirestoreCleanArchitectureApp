package ro.alexmamo.firestorecleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response

typealias Books = List<Book>
typealias BooksResponse = Response<Books>
typealias AddBookResponse = Response<Boolean>
typealias UpdateBookResponse = Response<Boolean>
typealias DeleteBookResponse = Response<Boolean>

interface BooksRepository {
    fun getBooks(): Flow<BooksResponse>

    suspend fun addBook(book: Book): AddBookResponse

    suspend fun updateBook(book: Book): UpdateBookResponse

    suspend fun deleteBook(id: String): DeleteBookResponse
}