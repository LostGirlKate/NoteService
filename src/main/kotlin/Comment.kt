package org.example

data class Comment(
    val noteId: Int,
    val message: String,
    val id: Int = 0,
    var isDeleted: Int = 0,
    val dateCreated: Long = System.currentTimeMillis()
)
