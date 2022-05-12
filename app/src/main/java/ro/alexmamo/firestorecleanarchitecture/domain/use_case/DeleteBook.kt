package ro.alexmamo.firestorecleanarchitecture.domain.use_case

import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

class DeleteBook(
    private val repo: BooksRepository
) {
    suspend operator fun invoke(bookId: String) = repo.deleteBookFromFirestore(bookId)
}