package ro.alexmamo.firestorecleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response

typealias Books = List<Book>
typealias BooksResponse = Response<Books>
typealias AddBookResponse = Response<Boolean>
typealias DeleteBookResponse = Response<Boolean>

interface BooksRepository {
    fun getBooksFromFirestore(): Flow<BooksResponse>

    fun addBookToFirestore(title: String, author: String): Flow<AddBookResponse>

    fun deleteBookFromFirestore(bookId: String): Flow<DeleteBookResponse>
}