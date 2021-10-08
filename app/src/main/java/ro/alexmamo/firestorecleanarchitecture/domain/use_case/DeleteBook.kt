package ro.alexmamo.firestorecleanarchitecture.domain.use_case

import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

class DeleteBook(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(bookId: String) = repository.deleteBookFromFirestore(bookId)
}