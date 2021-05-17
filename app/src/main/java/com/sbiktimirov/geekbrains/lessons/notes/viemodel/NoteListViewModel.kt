package com.sbiktimirov.geekbrains.lessons.notes.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData

class NoteListViewModel: ViewModel() {
    val notes = MutableLiveData<List<NoteData>>()

    init {
        val noteList = mutableListOf<NoteData>()
        for(i in 1..10) {
            noteList += NoteData(title = "Note title $i", description = "Note description")
        }
        notes.value = noteList
    }
}