package ro.alexmamo.firestorecleanarchitecture.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.alexmamo.firestorecleanarchitecture.core.Constants.BOOKS
import ro.alexmamo.firestorecleanarchitecture.data.repository.BooksRepositoryImpl
import ro.alexmamo.firestorecleanarchitecture.domain.repository.BooksRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideBooksRepository(
        db: FirebaseFirestore
    ): BooksRepository = BooksRepositoryImpl(
        booksRef = db.collection(BOOKS)
    )
}