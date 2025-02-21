package ro.alexmamo.firestorecleanarchitecture.domain.model

import com.google.firebase.firestore.Exclude

data class Book(
    val author: String? = null,
    @get:Exclude
    var id: String,
    val title: String? = null
)