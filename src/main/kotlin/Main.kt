import controller.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.CBORSerializer
import persistence.XMLSerializer
import utils.IndexChecker.isValidIndex
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val logger = KotlinLogging.logger{}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(XMLSerializer(File("notes.json")))
//private val noteAPI = NoteAPI(CBORSerializer("notes.cbor"))
fun showMenu() : Int {
    return readNextInt ("""
        > ---------------------------
        > |  NOTE KEEPER APP        |
        > ---------------------------
        > | NOTE MENU               |
        > |   1) Add a note         |
        > |   2) List notes         |
        > |   3) Update a note      |
        > |   4) Delete a note      |
        > |   5) Archive a note     |
        > |   6) Search by title    |
        > |   7) Mark as done       |
        > ---------------------------
        > |   20) Save notes        |
        > |   21) Load notes        |
        > ---------------------------
        > |   0) Exit               |
        > ---------------------------
        > ==>> 
    """.trimMargin(">"))
}
fun runMenu() {
    do {
        when (val menu = showMenu()) {
            1 -> addNote()
            2 -> readNotes()
            20 -> saveNote()
            21 -> loadNote()
            3 -> updateNote()
            4 -> deleteNote()
            5 -> archiveNote()
            6 -> searchNote()
            7 -> markAsDone()
            0 -> exitProgram()
            -99 -> addSomeNotes()
            else -> println("Option $menu is invalid. Try another one")
        }
    } while (true)
}


fun addNote() {
    //logger.info { "addNote() function invoked" }
    val title = readNextLine("Title of Note: ")
    val prio = readNextInt("Priority of Note (1-5): ")
    val category = readNextLine("Category of Note: ")
    val report = noteAPI.add(Note(title, prio, category))
    println ("""
        |
        |_______________________________________
        |Note with
        |   TITLE:      $title
        |   PRIORITY:   $prio
        |   CATEGORY:   $category
        |could ${if (report) "be added successfully" else "not be added. Error!"}
        |_______________________________________
        |
    """.trimMargin())
}

fun readNotes() {
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > |   4) View DONE notes         |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> readAllNotes()
            2 -> readActiveNotes()
            3 -> readArchivedNotes()
            4 -> readDoneNotes()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }

}
fun readDoneNotes() {
    println(noteAPI.listDoneNotes())
}
fun readActiveNotes() {
    println(noteAPI.listActiveNotes())
}
fun readArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}
fun readAllNotes() {
    println(noteAPI.listAllNotes())
}

fun updateNote() {
    //logger.info { "updateNote() function invoked" }
    readNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (isValidIndex(indexToUpdate, noteAPI.getNotes())) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val noteCategory = readNextLine("Enter a category for the note: ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteNote() {
    //logger.info { "deleteNote() function invoked" }
    readNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveNote() {
    readActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

fun searchNote() {
    if (noteAPI.numberOfNotes() > 0) {
        val notesByTitle : String = noteAPI.searchByTitle(readNextLine("Enter a title for the note: "))
        if (notesByTitle.isEmpty()) {
            println("No notes found")
        } else {
            println(notesByTitle)
        }
    }
}

fun markAsDone() {
    readActiveNotes()
    if (noteAPI.numberOfActiveNotes() > 0) {
        val indexToMarkAsDone = readNextInt("Enter the index of the note to mark as done: ")
        if (noteAPI.doneNote(indexToMarkAsDone)) {
            println("Successfully marked as done!")
        } else {
            println("Not successfully marked as done!")
        }
    }
}

fun exitProgram() {
    logger.info { "bye, see you :)" }
    kotlin.system.exitProcess(0)
}

fun saveNote() {
    try {
        noteAPI.store()
    } catch (e:Exception) {
        System.err.println("Error writing to file: $e")
    }
}
fun loadNote() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading to file: $e")
    }
}

fun addSomeNotes() {
    noteAPI.add(Note("Archived1", 2, "cat", true))
    noteAPI.add(Note("Archived2", 1, "noCat", true))
    noteAPI.add(Note("Archived3", 4, "cat", true))
    noteAPI.add(Note("Active1", 4, "cat", false))
    noteAPI.add(Note("Active2", 1, "cat", false))
    noteAPI.add(Note("Active3", 2, "noCat", false))
}

fun main() {
    //CBORSerializer("notes.cbor").read()//write(Note("Archived22", 2, "cat", true))
    runMenu()
}