package org.example

fun main() {
    val noteService = NoteService()
    noteService.add("Заметка 1", "Текст заметки 1")
    Thread.sleep(1_000)
    noteService.add("Заметка 2", "Текст заметки 2")
    Thread.sleep(1_000)
    noteService.add("Заметка 3", "Текст заметки 3")

    println(noteService.get())
    println(noteService.get(count = 2, sort = 1))
    println(noteService.getById(2))
    println(noteService.edit(2, "Заметка 2", "Текст заметки 2 отредактирован"))
    println(noteService.getById(2))
    println(noteService.delete(2))
    println(noteService.createComment(1, "Комментарий 1"))
    Thread.sleep(1_000)
    println(noteService.createComment(1, "Комментарий 2"))
    Thread.sleep(1_000)
    println(noteService.createComment(1, "Комментарий 3"))
    println(noteService.getComments(1))
    println(noteService.editComment(1, "Комментарий 1 измененный"))
    println(noteService.deleteComment(2))
    println(noteService.getComments(1))
    println(noteService.restoreComment(2))
    println(noteService.getComments(1))

    println(noteService.get("1,3", 0, 1))
}