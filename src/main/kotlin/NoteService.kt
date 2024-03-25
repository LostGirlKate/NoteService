package org.example

class NoteService {
    private val noteList = mutableListOf<Note>()
    private val commentList = mutableListOf<Comment>()
    private var lastNoteID: Int = 0
    private var lastCommentID: Int = 0

    fun add(title: String, text: String, privacy: Int = 0, commentPrivacy: Int = 0): Int {
        noteList.add(Note(title, text, privacy, commentPrivacy, 0, ++lastNoteID))
        return noteList.last().id
    }

    fun createComment(noteId: Int, message: String): Int {
        checkNote(noteId)
        commentList.add(Comment(noteId, message, ++lastCommentID))
        return commentList.last().id
    }

    fun delete(noteId: Int): Int {
        checkNote(noteId)
        return if (commentList.any { it.noteId == noteId })
            booleanToInt((noteList.removeIf { it.id == noteId } && commentList.removeIf { it.noteId == noteId }))
        else
            booleanToInt(noteList.removeIf { it.id == noteId })
    }

    fun deleteComment(commentID: Int): Int {
        checkComment(commentID)
        commentList.find { it.id == commentID && it.isDeleted == 0 }?.isDeleted = 1
        return booleanToInt(commentList.any { it.id == commentID && it.isDeleted == 1 })
    }

    fun edit(noteId: Int, title: String, text: String, privacy: Int = 0, commentPrivacy: Int = 0): Int {
        checkNote(noteId)
        val noteIndex = noteList.indexOfFirst { it.id == noteId }
        val editedNote =
            noteList[noteIndex].copy(title = title, text = text, privacy = privacy, commentPrivacy = commentPrivacy)
        noteList[noteIndex] = editedNote
        return booleanToInt(noteList[noteIndex] == editedNote)
    }

    fun editComment(commentID: Int, message: String): Int {
        checkComment(commentID)
        val commentIndex = commentList.indexOfFirst { it.id == commentID }
        val editedComment = commentList[commentIndex].copy(message = message)
        commentList[commentIndex] = editedComment
        return booleanToInt(commentList[commentIndex] == editedComment)
    }

    private fun checkComment(commentID: Int) {
        if (commentList.none { it.id == commentID && it.isDeleted == 0 }) throw CommentNotFoundException("Не найден комментарий с id: $commentID")
    }

    private fun checkNote(noteId: Int) {
        if (noteList.none { it.id == noteId }) throw NoteNotFoundException("Не найдена заметка с id: $noteId")
    }

    fun get(noteIds: String = "", count: Int = 0, sort: Int = 0): List<Note> {
        val resultList = if (noteIds == "") noteList
        else {
            val noteIdsList = noteIds.split(",").map { it.toInt() }.toList()
            val noteIndexes = mutableSetOf<Int>()
            noteIdsList.forEach { itemID -> noteIndexes.add(noteList.indexOfFirst { it.id == itemID }) }
            noteList.slice(noteIndexes)
        }

        val lastShowIndex = if (count > 0 && count <= resultList.size) count - 1 else resultList.lastIndex

        return if (sort == 0) {
            resultList.sortedByDescending { it.dateCreated }.slice(0..lastShowIndex)
        } else resultList.sortedBy { it.dateCreated }.slice(0..lastShowIndex)
    }

    fun getById(noteId: Int): Note {
        checkNote(noteId)
        return noteList.first { it.id == noteId }
    }

    fun getComments(noteID: Int, count: Int = 0, sort: Int = 0): List<Comment> {
        checkNote(noteID)
        val lastShowIndex =
            if (count > 0 && count <= commentList.filter { it.isDeleted == 0 }.size) count - 1 else commentList.filter { it.isDeleted == 0 }.lastIndex
        return if (sort == 0) commentList.filter { it.isDeleted == 0 }.sortedByDescending { it.dateCreated }
            .slice(0..lastShowIndex)
        else commentList.filter { it.isDeleted == 0 }.sortedBy { it.dateCreated }.slice(0..lastShowIndex)
    }


    fun restoreComment(commentID: Int): Int {
        if (commentList.none { it.id == commentID }) throw CommentNotFoundException("Не найден комментарий с id: $commentID")
        commentList.find { it.id == commentID && it.isDeleted == 1 }?.isDeleted = 0
        return booleanToInt(commentList.any { it.id == commentID && it.isDeleted == 0 })
    }

    private fun booleanToInt(b: Boolean) = if (b) 1 else 0
}