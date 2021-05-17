package com.sbiktimirov.geekbrains.lessons.notes.viemodel

import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbiktimirov.geekbrains.lessons.notes.R
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.fragment.NoteDetailFragment
import com.sbiktimirov.geekbrains.lessons.notes.repository.NoteDataRepository
import java.util.*

class NoteListViewModel : ViewModel() {
    var notes = MutableLiveData<List<NoteData>>()

    val note = MutableLiveData<NoteData>()

    val noteDataRepository = NoteDataRepository()

    init {
        loadNotes()
    }

    fun loadNotes() {
        notes = noteDataRepository.loadNotes()
    }

    fun addNote(): NoteData {
        return noteDataRepository.addNote(NoteData())
    }

    fun deleteNote(uuid: UUID) {
        noteDataRepository.deleteNote(uuid)
    }

    fun loadNote(uuid: UUID): NoteData? {
        return noteDataRepository.loadNote(uuid)
    }

    // TODO: 10.04.2021 2 Сделать вызов из фрагмента
    fun showNote(uuid: UUID, fragment: Fragment) {
        val note = loadNote(uuid)
        note?.let {
            showNote(it, fragment)
        }
    }


    fun showNote(noteData: NoteData, fragment: Fragment) {
        if (fragment.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragment.requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.note_list_container, fragment)
                .replace(R.id.note_detail_container, NoteDetailFragment.newInstance(noteData))
                .addToBackStack(null)
                .commit()
        } else {
            fragment.requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.note_list_container, NoteDetailFragment.newInstance(noteData))
                .addToBackStack(null)
                .commit()
        }
    }
}