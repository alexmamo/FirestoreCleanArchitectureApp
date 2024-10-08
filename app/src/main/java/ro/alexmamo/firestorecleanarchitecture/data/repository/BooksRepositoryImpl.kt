package ro.alexmamo.firestorecleanarchitecture.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorecleanarchitecture.core.Constants.AUTHOR
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
    override fun getBooks() = callbackFlow {
        val snapshotListener = booksRef.orderBy(TITLE).addSnapshotListener { booksSnapshot, e ->
            val booksResponse = if (booksSnapshot != null) {
                val books = booksSnapshot.map { bookSnapshot ->
                    bookSnapshot.toBook()
                }
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

    override suspend fun addBook(book: Book) = try {
        booksRef.add(book).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun updateBook(book: Book) = try {
        book.id?.let { id ->
            booksRef.document(id).update(mapOf(
                AUTHOR to book.author,
                TITLE to book.title
            )).await()
        }
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun deleteBook(id: String) = try {
        booksRef.document(id).delete().await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }
}

fun QueryDocumentSnapshot.toBook() = Book(
    author = getString(AUTHOR),
    title = getString(TITLE)
).apply {
    id = getId()
}