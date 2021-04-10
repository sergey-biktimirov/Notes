package com.sbiktimirov.geekbrains.lessons.notes.viemodel

import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbiktimirov.geekbrains.lessons.notes.R
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.fragment.NoteDetailFragment
import java.util.*

class NoteListViewModel : ViewModel() {
    val notes = MutableLiveData<List<NoteData>>()

    val note = MutableLiveData<NoteData>()

    init {
        loadNotes()
    }

    fun loadNotes() {
        val noteList = mutableListOf<NoteData>()
        for (i in 1..10) {
            noteList += NoteData(title = "Note title $i", description = "Note description")
        }
        notes.value = noteList
    }

    fun loadNote(uuid: UUID): NoteData {
        // TODO: 10.04.2021 1 Добавить загрузку заметки из БД
        return NoteData()
    }

    // TODO: 10.04.2021 2 Сделать вызов из фрагмента
    fun showNote(uuid: UUID, fragment: Fragment) {
        val note = loadNote(uuid)
        showNote(note, fragment)
    }

    // TODO: 10.04.2021 3. Заприватить
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