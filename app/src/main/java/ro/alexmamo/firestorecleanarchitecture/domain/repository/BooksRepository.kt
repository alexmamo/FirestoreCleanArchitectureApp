package ro.alexmamo.firestorecleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response

interface BooksRepository {
    fun getBooksFromFirestore(): Flow<Response<List<Book>>>

    suspend fun addBookToFirestore(title: String, author: String): Flow<Response<Void?>>

    suspend fun deleteBookFromFirestore(bookId: String): Flow<Response<Void?>>
}