package ro.alexmamo.firestorecleanarchitecture.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TITLE
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.*
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(
    private val booksRef: CollectionReference
): BooksRepository {
    override fun getBooksFromFirestore() = callbackFlow {
        val snapshotListener = booksRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val books = snapshot.toObjects(Book::class.java)
                Success(books)
            } else {
                Error(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addBookToFirestore(title: String, author: String) = flow {
        try {
            emit(Loading)
            val id = booksRef.document().id
            val book = Book(
                id = id,
                title = title,
                author = author
            )
            val addition = booksRef.document(id).set(book).await()
            emit(Success(addition))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    override suspend fun deleteBookFromFirestore(bookId: String) = flow {
        try {
            emit(Loading)
            val deletion = booksRef.document(bookId).delete().await()
            emit(Success(deletion))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }
}