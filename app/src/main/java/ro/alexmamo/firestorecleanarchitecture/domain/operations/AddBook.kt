package ro.alexmamo.firestorecleanarchitecture.domain.operations

import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

class AddBook(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(
        title: String,
        author: String
    ) = repository.addBookToFirestore(title, author)
}