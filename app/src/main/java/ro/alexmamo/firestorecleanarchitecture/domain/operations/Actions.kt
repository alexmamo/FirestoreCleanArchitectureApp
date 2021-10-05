package ro.alexmamo.firestorecleanarchitecture.domain.operations

data class Actions (
    val getBooks: GetBooks,
    val addBook: AddBook,
    val deleteBook: DeleteBook
)