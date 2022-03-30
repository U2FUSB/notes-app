package persistence

import kotlinx.serialization.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.*
import kotlinx.serialization.encodeToHexString
import models.Note
import java.io.File
import kotlin.jvm.Throws

@Serializable
class CBORSerializer(private val fileName: String) : Serializer {

    @OptIn(ExperimentalSerializationApi::class)
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val cborData = Cbor.encodeToHexString(obj.toString())
        File(fileName).printWriter().use { out ->
            out.write(cborData)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Throws(Exception::class)
    override fun read(): Any {
        val cborAsString = Cbor.decodeFromHexString<String>(File(fileName).bufferedReader().readLine())
        val cborStringConverted = cborAsString.removeRange(0, 5).dropLast(1).split(",").toTypedArray()

        val noteTitle = cborStringConverted[0].removeRange(0, 10)
        val notePriority = cborStringConverted[1].removeRange(0, 14).toInt()
        val noteCategory = cborStringConverted[2].removeRange(0, 14)
        val isNoteArchived = cborStringConverted[3].removeRange(0, 14).toBoolean()
        val isNoteDone = cborStringConverted[4].removeRange(0, 10).toBoolean()

        return Note(noteTitle, notePriority, noteCategory, isNoteArchived, isNoteDone)
    }
}