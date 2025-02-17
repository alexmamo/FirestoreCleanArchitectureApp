package ro.alexmamo.firestorecleanarchitecture.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BookListRepository

const val AUTHOR_FIELD = "author"
const val TITLE_FIELD = "title"

class BookListRepositoryImpl(
    private val booksRef: CollectionReference
): BookListRepository {
    override fun getBookList() = callbackFlow {
        val listener = booksRef.orderBy(TITLE_FIELD).addSnapshotListener { bookListSnapshot, e ->
            val bookListResponse = if (bookListSnapshot != null) {
                val bookList = bookListSnapshot.map { bookSnapshot ->
                    bookSnapshot.toBook()
                }
                Response.Success(bookList)
            } else {
                Response.Failure(e)
            }
            trySend(bookListResponse)
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun addBook(book: Book) = try {
        val bookId = booksRef.add(book).await().id
        Response.Success(bookId)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateBook(book: Book) = try {
        val void = book.id?.let { bookId ->
            booksRef.document(bookId).update(mapOf(
                AUTHOR_FIELD to book.author,
                TITLE_FIELD to book.title
            )).await()
        }
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteBook(bookId: String) = try {
        val void = booksRef.document(bookId).delete().await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

fun DocumentSnapshot.toBook() = Book(
    author = getString(AUTHOR_FIELD),
    title = getString(TITLE_FIELD)
).apply {
    id = getId()
}