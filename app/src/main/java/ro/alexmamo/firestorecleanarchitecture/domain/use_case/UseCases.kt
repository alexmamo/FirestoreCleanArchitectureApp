package ro.alexmamo.firestorecleanarchitecture.domain.use_case

data class UseCases (
    val getBooks: GetBooks,
    val addBook: AddBook,
    val deleteBook: DeleteBook
)