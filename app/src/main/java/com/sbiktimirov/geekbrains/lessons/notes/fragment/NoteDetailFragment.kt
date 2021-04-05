package com.sbiktimirov.geekbrains.lessons.notes.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.R

private const val NOTE_ID = "note_id"

class NoteDetailFragment : Fragment() {
    private var noteData: NoteData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let{
            noteData = it.getSerializable(NOTE_ID) as NoteData
        }

        val root = inflater.inflate(R.layout.fragment_note_detail, container, false)

        updateUI(root)

        return root
    }

    private fun updateUI(view: View) {
        arguments?.let{
            noteData = it.getSerializable(NOTE_ID) as NoteData

            view.findViewById<TextView>(R.id.note_title).text = noteData?.title
            view.findViewById<TextView>(R.id.note_date).text = noteData?.date.toString()
            view.findViewById<TextView>(R.id.note_description).text = noteData?.description
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (noteData != null ) outState.putSerializable(NOTE_ID, noteData)
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