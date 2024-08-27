package ro.alexmamo.firestorecleanarchitecture.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Firestore
    const val BOOKS = "books"
    const val AUTHOR = "author"
    const val TITLE = "title"

    //Actions
    const val ADD_BOOK = "Add a book"
    const val EDIT_BOOK = "Edit a book"
    const val DELETE_BOOK = "Delete a book"

    //Buttons
    const val ADD_BUTTON = "Add"
    const val UPDATE_BUTTON = "Update"
    const val DISMISS_BUTTON = "Dismiss"

    //Placeholders
    const val AUTHOR_NAME = "Type the author name..."
    const val BOOK_TITLE = "Type a book title..."
    const val EMPTY_STRING = ""

    //Messages
    const val EMPTY_TITLE_MESSAGE = "Title cannot be empty."
    const val EMPTY_AUTHOR_MESSAGE = "Author cannot be empty."
}