package ro.alexmamo.firestorecleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response

typealias Books = List<Book>
typealias BooksResponse = Response<Books>
typealias AddBookResponse = Response<Unit>
typealias UpdateBookResponse = Response<Unit>
typealias DeleteBookResponse = Response<Unit>

interface BooksRepository {
    fun getBooks(): Flow<BooksResponse>

    suspend fun addBook(book: Book): AddBookResponse

    suspend fun updateBook(book: Book): UpdateBookResponse

    suspend fun deleteBook(id: String): DeleteBookResponse
}