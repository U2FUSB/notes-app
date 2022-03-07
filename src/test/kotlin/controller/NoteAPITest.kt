package controller

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class NoteAPITest {

    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var iAmArchived: Note? = null
    private var iAmTooArchived: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI()
    private var emptyNotes: NoteAPI? = NoteAPI()
    private var archivedNotes: NoteAPI? = NoteAPI()

    @BeforeEach
    fun setup(){
        learnKotlin = Note("Learning Kotlin", 5, "College", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", false)
        swim = Note("Swim - Pool", 3, "Hobby", false)
        iAmArchived = Note("archvedNote", 2, "some", true)
        iAmTooArchived = Note("alsoArchived", 1, "some", true)

        //adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)

        //adding 2 Note to the notes api (for archived)
        archivedNotes!!.add(iAmArchived!!)
        archivedNotes!!.add(iAmTooArchived!!)
    }

    @AfterEach
    fun tearDown(){
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        iAmArchived = null
        iAmTooArchived = null

        populatedNotes = null
        emptyNotes = null
        archivedNotes = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`(){
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(6, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`(){
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfNotes())
            assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
        }
    }

    @Nested
    inner class ListNotes {
        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
        }
    }

    @Nested
    inner class ListArchivedNotes {
        @Test
        fun `listArchivedNotes returns just archived notes` () {
           /* assertTrue(iAmArchived!!.isNoteArchived)
            assertTrue(iAmTooArchived!!.isNoteArchived)
            assertFalse(learnKotlin!!.isNoteArchived)
            assertFalse(swim!!.isNoteArchived)*/
            assertTrue(archivedNotes!!.numberOfArchivedNotes() == 2)
        }
        @Test
        fun `listArchivedNotes returns no archived notes, when there are none` () {
            /*assertFalse(iAmArchived!!.isNoteArchived)
            assertFalse(iAmTooArchived!!.isNoteArchived)
            assertTrue(learnKotlin!!.isNoteArchived)
            assertTrue(swim!!.isNoteArchived)*/
            assertTrue(populatedNotes!!.numberOfArchivedNotes() == 0)
        }
    }

    @Nested
    inner class  ListActiveNotes {
        @Test
        fun `listActiveNotes returns just active notes` () {
            assertTrue(populatedNotes!!.numberOfActiveNotes() == 5)
            assertFalse(archivedNotes!!.numberOfArchivedNotes() == 0)
        }
        @Test
        fun `listActiveNotes returns no active notes, when there are none` () {
            assertFalse(populatedNotes!!.numberOfActiveNotes() == 0)
        }
    }
    @Nested
    inner class ListPrioritys {
        @Test
        fun `listNotesBySelectedPriority returns just notes of priority 1` () {
            assertFalse(populatedNotes!!.listNotesBySelectedPriority(1).contains(swim.toString()))
            assertTrue(populatedNotes!!.listNotesBySelectedPriority(1).contains(summerHoliday.toString()))
        }
    }
    @Nested
    inner class CountPrioritys {
        @Test
        fun `numberOfNotesByPriority returns just fitting sums of elements` () {
            assertEquals(2, populatedNotes!!.numberOfNotesByPriority(4))
            assertNotEquals(0, populatedNotes!!.numberOfNotesByPriority(1))
        }
    }
}