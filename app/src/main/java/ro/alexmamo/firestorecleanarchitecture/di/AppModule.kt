package ro.alexmamo.firestorecleanarchitecture.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ro.alexmamo.firestorecleanarchitecture.data.repository.BooksRepositoryImpl
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository
import ro.alexmamo.firestorecleanarchitecture.domain.use_case.*
import ro.alexmamo.firestorecleanarchitecture.core.Constants.BOOKS
import ro.alexmamo.firestorecleanarchitecture.core.Constants.TITLE

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun provideBooksRef(db: FirebaseFirestore) = db.collection(BOOKS)

    @Provides
    fun provideBooksQuery(booksRef: CollectionReference) = booksRef.orderBy(TITLE)

    @Provides
    fun provideBooksRepository(
        booksRef: CollectionReference,
        booksQuery: Query
    ): BooksRepository = BooksRepositoryImpl(booksRef, booksQuery)

    @Provides
    fun provideUseCases(repository: BooksRepository) = UseCases(
        getBooks = GetBooks(repository),
        addBook = AddBook(repository),
        deleteBook = DeleteBook(repository)
    )
}