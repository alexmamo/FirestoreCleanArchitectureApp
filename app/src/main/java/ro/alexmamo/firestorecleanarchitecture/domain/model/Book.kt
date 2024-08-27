package ro.alexmamo.firestorecleanarchitecture.domain.model

import com.google.firebase.firestore.Exclude

data class Book(
    val author: String? = null,
    @Exclude
    var id: String? = null,
    val title: String? = null
)