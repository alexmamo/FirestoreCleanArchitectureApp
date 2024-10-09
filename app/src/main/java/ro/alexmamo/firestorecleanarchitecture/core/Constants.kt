package ro.alexmamo.firestorecleanarchitecture.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Firestore Collections
    const val BOOKS = "books"
    //Document Fields
    const val AUTHOR = "author"
    const val TITLE = "title"

    //Actions
    const val ADD_BOOK = "Add a book"
    const val UPDATE_BOOK = "Update a book"

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
    const val NO_UPDATES_MESSAGE = "No updates performed."
    const val EMPTY_BOOK_LIST_MESSAGE = "The book list is empty."
}