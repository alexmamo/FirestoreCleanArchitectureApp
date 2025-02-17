package ro.alexmamo.firestorecleanarchitecture.domain.model

sealed class BookError {
    object EmptyTitle : BookError()
    object EmptyAuthor : BookError()
}