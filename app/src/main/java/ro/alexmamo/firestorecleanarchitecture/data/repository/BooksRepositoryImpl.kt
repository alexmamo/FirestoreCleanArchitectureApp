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

    override suspend fun addBook(book: MutableMap<String, Any>) = try {
        booksRef.add(book).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun getBookById(bookId: String) = try {
        val book = booksRef.document(bookId).get().await().toObject(Book::class.java)?.apply {
            id = bookId
        }
        Success(book)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun editBook(bookId: String, book: MutableMap<String, Any>) = try {
        booksRef.document(bookId).update(book).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun deleteBookById(bookId: String) = try {
        booksRef.document(bookId).delete().await()
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