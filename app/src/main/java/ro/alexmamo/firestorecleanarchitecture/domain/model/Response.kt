package ro.alexmamo.firestorecleanarchitecture.domain.model

sealed class Response<out T> {
    data object Idle : Response<Nothing>()

    data object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Response<T>()

    data class Failure(
        val e: Exception?
    ) : Response<Nothing>()
}