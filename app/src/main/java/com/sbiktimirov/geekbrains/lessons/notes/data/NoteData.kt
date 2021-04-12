package com.sbiktimirov.geekbrains.lessons.notes.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable
import java.util.*

@IgnoreExtraProperties
data class NoteData(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val date: Date = Date()
): Serializable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id.toString(),
            "title" to title,
            "description" to description,
            "date" to date.time
        )
    }
}

fun QueryDocumentSnapshot.toNoteData(): NoteData {
    return NoteData(
        UUID.fromString(this.get("id") as String),
        this.get("title").toString(),
        this.get("description").toString(),
        Date(this.get("date") as Long)
    )
}