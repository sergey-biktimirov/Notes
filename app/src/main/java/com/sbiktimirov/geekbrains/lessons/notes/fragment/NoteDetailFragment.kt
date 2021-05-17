package com.sbiktimirov.geekbrains.lessons.notes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.R
import com.sbiktimirov.geekbrains.lessons.notes.viemodel.NoteListViewModel

private const val NOTE_ID = "note_id"

class NoteDetailFragment : Fragment() {
    private val noteListViewModel: NoteListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            arguments = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            noteListViewModel.note.value = it.getSerializable(NOTE_ID) as NoteData?
        }

        val root = inflater.inflate(R.layout.fragment_note_detail, container, false)

        updateUI(root)

        return root
    }

    private fun updateUI(view: View) {
        view.findViewById<TextView>(R.id.note_title).text = noteListViewModel.note.value?.title
        view.findViewById<TextView>(R.id.note_date).text =
            noteListViewModel.note.value?.date.toString()
        view.findViewById<TextView>(R.id.note_description).text =
            noteListViewModel.note.value?.description
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(NOTE_ID, noteListViewModel.note.value)
    }

    companion object {
        @JvmStatic
        fun newInstance(noteData: NoteData) =
            NoteDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(NOTE_ID, noteData)
                }
            }
    }
}