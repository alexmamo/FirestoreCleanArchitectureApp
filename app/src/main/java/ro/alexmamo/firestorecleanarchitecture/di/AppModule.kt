package ro.alexmamo.firestorecleanarchitecture.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.alexmamo.firestorecleanarchitecture.data.repository.BookListRepositoryImpl
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BookListRepository

const val BOOKS = "books"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBookListRepository(): BookListRepository = BookListRepositoryImpl(
        booksRef = Firebase.firestore.collection(BOOKS)
    )
}