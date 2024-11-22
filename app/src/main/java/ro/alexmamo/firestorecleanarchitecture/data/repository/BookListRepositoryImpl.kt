package ro.alexmamo.firestorecleanarchitecture.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorecleanarchitecture.domain.model.Book
import ro.alexmamo.firestorecleanarchitecture.domain.model.Response
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BookListRepository

const val AUTHOR = "author"
const val TITLE = "title"

class BookListRepositoryImpl(
    private val booksRef: CollectionReference
): BookListRepository {
    override fun getBookList() = callbackFlow {
        val listener = booksRef.orderBy(TITLE).addSnapshotListener { bookListSnapshot, e ->
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
        val id = booksRef.add(book).await().id
        Response.Success(id)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateBook(book: Book) = try {
        val void = book.id?.let { id ->
            booksRef.document(id).update(mapOf(
                AUTHOR to book.author,
                TITLE to book.title
            )).await()
        }
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteBook(id: String) = try {
        val void = booksRef.document(id).delete().await()
        Response.Success(void)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

fun DocumentSnapshot.toBook() = Book(
    author = getString(AUTHOR),
    title = getString(TITLE)
).apply {
    id = getId()
}