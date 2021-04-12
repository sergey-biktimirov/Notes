package com.sbiktimirov.geekbrains.lessons.notes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.firestore.FirebaseFirestore
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.data.toNoteData
import com.sbiktimirov.geekbrains.lessons.notes.extension.minusAssign
import com.sbiktimirov.geekbrains.lessons.notes.extension.plusAssign
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import java.util.*

interface INoteDataRepository {
    fun loadNote(uuid: UUID): NoteData?
    fun loadNotes(): LiveData<List<NoteData>>
    fun addNote(noteData: NoteData): NoteData
    fun deleteNote(uuid: UUID): NoteData?
    fun updateNote(noteData: NoteData): NoteData
}

private const val NOTES_COLLECTION = "notes"

class NoteDataRepository : INoteDataRepository {
    private val notes = MutableLiveData<List<NoteData>>()
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun loadNote(uuid: UUID): NoteData? {
        var noteData: NoteData? = null
        notesReference
            .whereEqualTo("id", uuid.toString())
            .get()
            .addOnCompleteListener {
                noteData = it.result?.firstOrNull()?.toNoteData()
            }
        return noteData
    }

    override fun loadNotes(): MutableLiveData<List<NoteData>> {
        notesReference
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.let { res ->
                        notes.value = res.map {v ->
                            v.toNoteData()
                        }
                    }
                }
            }
        return notes
    }

    override fun addNote(noteData: NoteData): NoteData {
        notesReference
            .add(noteData.toMap())
            .addOnSuccessListener {
                notes += noteData
            }
        return noteData
    }

    override fun deleteNote(uuid: UUID): NoteData? {
        val note = loadNote(uuid)

        note?.let {
            notesReference
                .document(uuid.toString())
                .delete()
                .addOnSuccessListener {
                    notes -= note
                }
        }

        return note
    }

    override fun updateNote(noteData: NoteData): NoteData {
        TODO("Not yet implemented")
    }
}