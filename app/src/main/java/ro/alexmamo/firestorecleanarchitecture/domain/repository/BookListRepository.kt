package ro.alexmamo.firestorecleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response

typealias BookListResponse = Response<List<Book>>
typealias AddBookResponse = Response<String>
typealias UpdateBookResponse = Response<Void>
typealias DeleteBookResponse = Response<Void>

interface BookListRepository {
    fun getBookList(): Flow<BookListResponse>

    suspend fun addBook(book: Book): AddBookResponse

    suspend fun updateBook(book: Book): UpdateBookResponse

    suspend fun deleteBook(id: String): DeleteBookResponse
}