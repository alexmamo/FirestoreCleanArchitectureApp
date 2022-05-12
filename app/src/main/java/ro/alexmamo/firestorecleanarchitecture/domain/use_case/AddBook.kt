package ro.alexmamo.firestorecleanarchitecture.domain.use_case

import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

class AddBook(
    private val repo: BooksRepository
) {
    suspend operator fun invoke(
        title: String,
        author: String
    ) = repo.addBookToFirestore(title, author)
}