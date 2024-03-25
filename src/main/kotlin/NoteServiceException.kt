package org.example

sealed class NoteServiceException(override val message: String?) : Exception(message)

class NoteNotFoundException(message: String?) : NoteServiceException(message)
class CommentNotFoundException(message: String?) : NoteServiceException(message)