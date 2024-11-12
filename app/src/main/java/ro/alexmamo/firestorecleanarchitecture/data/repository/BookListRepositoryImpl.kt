package ro.alexmamo.firestorecleanarchitecture.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firestorecleanarchitecture.core.launchCatching
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

    override suspend fun addBook(book: Book) = launchCatching {
        booksRef.add(book).await().id
    }

    override suspend fun updateBook(book: Book) = launchCatching {
        book.id?.let { id ->
            booksRef.document(id).update(mapOf(
                AUTHOR to book.author,
                TITLE to book.title
            )).await()
        }
    }

    override suspend fun deleteBook(id: String) = launchCatching {
        booksRef.document(id).delete().await()
    }
}

fun QueryDocumentSnapshot.toBook() = Book(
    author = getString(AUTHOR),
    title = getString(TITLE)
).apply {
    id = getId()
}