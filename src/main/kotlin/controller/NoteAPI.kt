package controller

import models.Note

class NoteAPI {
    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }
    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }
    fun numberOfNotes(): Int {
        return notes.size
    }
    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }
    private fun isValidListIndex(index: Int, list: List<Any>):Boolean {
        return (index >= 0 && index < list.size)
    }
    fun listActiveNotes(): String {
        return notes.filter { !it.isNoteArchived }.toString()
    }

    fun listArchivedNotes(): String {
        return notes.filter{ it.isNoteArchived }.toString()
    }

    fun numberOfArchivedNotes(): Int {
        return notes.count { it.isNoteArchived }
    }

    fun numberOfActiveNotes(): Int {
        return notes.count{ !it.isNoteArchived }
    }

    fun listNotesBySelectedPriority(priority: Int): String {
        return notes.filter { it.notePriority == priority }.toString()
    }

    fun numberOfNotesByPriority(priority: Int): Int {
        return notes.count{it.notePriority == priority}
    }
}