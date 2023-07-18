package ro.alexmamo.firestorecleanarchitecture.data.repository

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TITLE
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Failure
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response.Success
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepositoryImpl @Inject constructor(
    private val booksRef: CollectionReference
): BooksRepository {
    override fun getBooksFromFirestore() = callbackFlow {
        val snapshotListener = booksRef.orderBy(TITLE).addSnapshotListener { snapshot, e ->
            val booksResponse = if (snapshot != null) {
                val books = snapshot.toObjects(Book::class.java)
                Success(books)
            } else {
                Failure(e)
            }
            trySend(booksResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addBookToFirestore(title: String, author: String) = try {
        val id = booksRef.document().id
        val book = Book(
            id = id,
            title = title,
            author = author
        )
        booksRef.document(id).set(book).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun deleteBookFromFirestore(bookId: String) = try {
        booksRef.document(bookId).delete().await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }
}