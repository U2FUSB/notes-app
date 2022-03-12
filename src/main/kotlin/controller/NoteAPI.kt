package controller
//
import models.Note

class NoteAPI {
    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }
    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
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
    fun listActiveNotes(): String {
        return if (numberOfActiveNotes() == 0) {
            "No active notes stored"
        } else {
            notes.filter { !it.isNoteArchived}.joinToString { "${notes.indexOf(it)}: $it" }
        }
    }
    fun listArchivedNotes(): String {
        return if (numberOfArchivedNotes() == 0) {
            "No archived notes stored"
        } else {
            notes.filter{ it.isNoteArchived }.joinToString { "${notes.indexOf(it)}: $it" }
        }
    }
    fun listNotesBySelectedPriority(priority: Int): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            if (numberOfNotesByPriority(priority) == 0) {
                "no notes with priority: $priority"
            } else {
                notes.filter { it.notePriority == priority }.joinToString { "${notes.indexOf(it)}: $it" }
            }
            // obsolete:
            /*val notesByPrio =
            notesByPrio.ifEmpty { */
        }
    }
    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }
    fun numberOfNotes(): Int {
        return notes.size
    }
    fun numberOfArchivedNotes(): Int {
        return notes.count { it.isNoteArchived }
    }
    fun numberOfActiveNotes(): Int {
        return notes.count{ !it.isNoteArchived }
    }
    fun numberOfNotesByPriority(priority: Int): Int {
        return notes.count{it.notePriority == priority}
    }
    private fun isValidListIndex(index: Int, list: List<Any>):Boolean {
        return (index >= 0 && index < list.size)
    }

}