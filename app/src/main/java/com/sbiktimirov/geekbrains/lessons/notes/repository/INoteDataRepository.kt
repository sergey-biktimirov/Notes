package com.sbiktimirov.geekbrains.lessons.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.extension.minusAssign
import com.sbiktimirov.geekbrains.lessons.notes.extension.plusAssign
import java.util.*

interface INoteDataRepository {
    fun loadNote(uuid: UUID): NoteData?
    fun loadNotes(): LiveData<List<NoteData>>
    fun addNote(noteData: NoteData): NoteData
    fun deleteNote(uuid: UUID): NoteData?
    fun updateNote(noteData: NoteData): NoteData
}

class NoteDataRepository : INoteDataRepository {
    private val notes = MutableLiveData<List<NoteData>>()

    override fun loadNote(uuid: UUID): NoteData? {
        return notes.value?.let { notes ->
            return@let notes.firstOrNull() { note ->
                note.id == uuid
            }
        }
    }

    override fun loadNotes(): MutableLiveData<List<NoteData>> {
        val noteList = mutableListOf<NoteData>()
        for (i in 1..10) {
            noteList += NoteData(title = "Note title $i", description = "Note description")
        }
        notes.value = noteList
        return notes
    }

    override fun addNote(noteData: NoteData): NoteData {
        notes += noteData
        return noteData
    }

    override fun deleteNote(uuid: UUID): NoteData? {
        val note = loadNote(uuid)

        note?.let {
            notes -= it
        }

        return note
    }

    override fun updateNote(noteData: NoteData): NoteData {
        TODO("Not yet implemented")
    }
}