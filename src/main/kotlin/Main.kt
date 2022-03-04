import utils.*

fun showMenu() : Int {
    return ScannerInput.readNextInt ("""
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
    println("bye, see you :)")
    kotlin.system.exitProcess(0)
}

fun deleteNote() {
    //TODO("Not yet implemented")
}

fun updateNote() {
    //TODO("Not yet implemented")
}

fun readNote() {
    //TODO("Not yet implemented")
}

fun addNote() {
    //TODO("Not yet implemented")
}

fun main() {
    runMenu()
}