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
            else -> println("Option $menu is invalid. Try another one")
        }
    } while (true)
}

fun exitProgram() {
    logger.info { "bye, see you :)" }
    kotlin.system.exitProcess(0)
}

fun deleteNote() {
    logger.info { "deleteNote() function invoked" }
}

fun updateNote() {
    logger.info { "updateNote() function invoked" }
}

fun readNote() {
    println(NoteAPI().listAllNotes())
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