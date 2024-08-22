package ro.alexmamo.firestorecleanarchitecture.domain.model

import com.google.firebase.firestore.Exclude

data class Book(
    var author: String? = null,
    @Exclude
    var id: String? = null,
    var title: String? = null
)