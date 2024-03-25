import org.example.*
import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addTest() {
        val service = NoteService()
        val result = service.add("Заметка 1", "Текст заметки 1")
        assertEquals(result, 1)
    }

    @Test
    fun createCommentTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        val result = service.createComment(1, "Комментарий 1")
        assertEquals(result, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentNoteNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(2, "Комментарий 1")
    }

    @Test
    fun deleteWithCommentTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        val result = service.delete(1)
        assertEquals(result, 1)
    }

    @Test
    fun deleteTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        val result = service.delete(1)
        assertEquals(result, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        service.delete(2)
    }

    @Test
    fun deleteCommentTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        service.createComment(1, "Комментарий 2")
        val result = service.deleteComment(1)
        assertEquals(result, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteCommentNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        service.deleteComment(1)
        service.deleteComment(1)
    }

    @Test
    fun editTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        val result = service.edit(1, "Заметка 1", "Текст заметки 1 измененный")
        assertEquals(result, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun editNoteNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.edit(2, "Заметка 1", "Текст заметки 1 измененный")
    }

    @Test
    fun editCommentTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        val result = service.editComment(1, "Комментарий 1 измененный")
        assertEquals(result, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun editCommentNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        service.editComment(2, "Комментарий 1 измененный")
    }

    @Test
    fun getDefaultTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        Thread.sleep(1_000)
        service.add("Заметка 2", "Текст заметки 2")
        val list = listOf(
            Note("Заметка 2", "Текст заметки 2", id = 2, dateCreated = service.getById(2).dateCreated),
            Note("Заметка 1", "Текст заметки 1", id = 1, dateCreated = service.getById(1).dateCreated)
        )
        val result = service.get()
        assertEquals(result, list)
    }

    @Test
    fun getSortTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        Thread.sleep(1_000)
        service.add("Заметка 2", "Текст заметки 2")
        Thread.sleep(1_000)
        service.add("Заметка 3", "Текст заметки 3")
        val list = listOf(
            Note("Заметка 1", "Текст заметки 1", id = 1, dateCreated = service.getById(1).dateCreated),
            Note("Заметка 2", "Текст заметки 2", id = 2, dateCreated = service.getById(2).dateCreated),
            Note("Заметка 3", "Текст заметки 3", id = 3, dateCreated = service.getById(3).dateCreated)
        )
        val result = service.get(sort = 1)
        assertEquals(result, list)
    }

    @Test
    fun getWithIndexTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        Thread.sleep(1_000)
        service.add("Заметка 2", "Текст заметки 2")
        Thread.sleep(1_000)
        service.add("Заметка 3", "Текст заметки 3")
        Thread.sleep(1_000)
        service.add("Заметка 4", "Текст заметки 4")
        val list = listOf(
            Note("Заметка 4", "Текст заметки 4", id = 4, dateCreated = service.getById(4).dateCreated),
            Note("Заметка 3", "Текст заметки 3", id = 3, dateCreated = service.getById(3).dateCreated)
        )
        val result = service.get("1,3,4", 2, 0)
        assertEquals(result, list)
    }

    @Test
    fun getByIdTest() {
        val service = NoteService()
        service.add("Заметка 4", "Текст заметки 4")
        val note = Note("Заметка 4", "Текст заметки 4", id = 1, dateCreated = service.getById(1).dateCreated)
        val result = service.getById(1)
        assertEquals(result, note)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getCommentsNoteNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        service.getComments(2)
    }

    @Test
    fun getCommentsDefaultTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        Thread.sleep(1_000)
        service.createComment(1, "Комментарий 2")
        Thread.sleep(1_000)
        service.createComment(1, "Комментарий 3")
        service.deleteComment(2)
        val result = service.getComments(1)
        val list = listOf(
            Comment(1, "Комментарий 3", 3, dateCreated = result[0].dateCreated),
            Comment(1, "Комментарий 1", 1, dateCreated = result[1].dateCreated)
        )
        assertEquals(result, list)
    }

    @Test
    fun getCommentsWithSortTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        Thread.sleep(1_000)
        service.createComment(1, "Комментарий 2")
        Thread.sleep(1_000)
        service.createComment(1, "Комментарий 3")
        val result = service.getComments(1, 2, 1)
        val list = listOf(
            Comment(1, "Комментарий 1", 1, dateCreated = result[0].dateCreated),
            Comment(1, "Комментарий 2", 2, dateCreated = result[1].dateCreated)
        )
        assertEquals(result, list)
    }

    @Test
    fun restoreCommentTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.createComment(1, "Комментарий 1")
        service.createComment(1, "Комментарий 2")
        service.createComment(1, "Комментарий 3")
        service.deleteComment(2)
        val result = service.restoreComment(2)
        assertEquals(result, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreCommentNotFoundExceptionTest() {
        val service = NoteService()
        service.add("Заметка 1", "Текст заметки 1")
        service.add("Заметка 2", "Текст заметки 2")
        service.createComment(1, "Комментарий 1")
        service.createComment(1, "Комментарий 2")
        service.deleteComment(2)
        service.delete(1)
        service.restoreComment(2)
    }
}