package ro.alexmamo.firestorecleanarchitecture.domain.operations

import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

class GetBooks (
    private val repository: BooksRepository
) {
    operator fun invoke() = repository.getBooksFromFirestore()
}