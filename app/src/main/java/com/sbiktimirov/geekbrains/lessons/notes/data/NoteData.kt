package com.sbiktimirov.geekbrains.lessons.notes.data

import java.io.Serializable
import java.util.*

data class NoteData(
    val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val date: Date = Date()
): Serializable