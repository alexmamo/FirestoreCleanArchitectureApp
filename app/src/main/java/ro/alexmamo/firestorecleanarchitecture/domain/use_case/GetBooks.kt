package ro.alexmamo.firestorecleanarchitecture.domain.use_case

import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

class GetBooks (
    private val repo: BooksRepository
) {
    operator fun invoke() = repo.getBooksFromFirestore()
}