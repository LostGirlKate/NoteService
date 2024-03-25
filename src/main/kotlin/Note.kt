package org.example

data class Note(
    val title: String,
    val text: String,
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    val isDeleted: Int = 0,
    val id: Int = 0,
    val dateCreated: Long = System.currentTimeMillis()

)