import controller.NoteAPI
import models.Note
import mu.KotlinLogging
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

private val logger = KotlinLogging.logger{}
private val noteAPI = NoteAPI()

fun showMenu() : Int {
    return readNextInt ("""
        > ---------------------------
        > |  NOTE KEEPER APP        |
        > ---------------------------
        > | NOTE MENU               |
        > |   1) Add a note         |
        > |   2) List all notes     |
        > |   3) Update a note      |
        > |   4) Delete a note      |
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
            2 -> readNote()
            3 -> updateNote()
            4 -> deleteNote()
            0 -> exitProgram()
            -99 -> addSomeNotes()
            else -> println("Option $menu is invalid. Try another one")
        }
    } while (true)
}

fun addSomeNotes() {
    noteAPI.add(Note("Archived1", 2, "cat", true))
    noteAPI.add(Note("Archived2", 1, "noCat", true))
    noteAPI.add(Note("Archived3", 4, "cat", true))
    noteAPI.add(Note("Active1", 4, "cat", false))
    noteAPI.add(Note("Active2", 1, "cat", false))
    noteAPI.add(Note("Active3", 2, "noCat", false))
}
fun exitProgram() {
    logger.info { "bye, see you :)" }
    kotlin.system.exitProcess(0)
}
fun deleteNote() {
    //logger.info { "deleteNote() function invoked" }
    readNote()
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
fun updateNote() {
    logger.info { "updateNote() function invoked" }
}
fun readNote() {
    println(noteAPI.listAllNotes())
    println("\nActive Notes are:")
    println(noteAPI.listActiveNotes())
    println("in sum: ${noteAPI.numberOfActiveNotes()}\n")
    println("\nArchived Notes are:")
    println(noteAPI.listArchivedNotes())
    println("in sum: ${noteAPI.numberOfArchivedNotes()}\n")
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

fun main() {
    runMenu()
}