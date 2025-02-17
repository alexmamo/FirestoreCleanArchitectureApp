package ro.alexmamo.firestorecleanarchitecture.domain.model

sealed class BookError {
    object EmptyBookTitle : BookError()
    object EmptyBookAuthor : BookError()
}