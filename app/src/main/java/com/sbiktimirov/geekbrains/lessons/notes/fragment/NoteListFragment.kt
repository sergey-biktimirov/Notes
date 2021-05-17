package com.sbiktimirov.geekbrains.lessons.notes.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.R
import com.sbiktimirov.geekbrains.lessons.notes.extension.createRecycleViewListAdapter
import com.sbiktimirov.geekbrains.lessons.notes.viemodel.NoteListViewModel

class NoteListFragment : Fragment() {

    private val noteListViewModel: NoteListViewModel by activityViewModels()
    private lateinit var noteListRecycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_note_list, container, false)

        val adapter = createRecycleViewListAdapter<NoteData>(R.layout.note_list_item) { view, note ->
            view.findViewById<TextView>(R.id.note_title).text = note.title
            view.findViewById<TextView>(R.id.note_date).text = note.date.toString()
            view.setOnClickListener {
                showNote(note)
            }
        }

        noteListRecycleView = root.findViewById(R.id.note_list_recycle_view) as RecyclerView
        noteListRecycleView.layoutManager = LinearLayoutManager(context)
        noteListRecycleView.adapter = adapter

        noteListViewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return root
    }

    fun showNote(noteData: NoteData) {
        if(requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NoteListFragment())
                .replace(R.id.note_detail_fragment, NoteDetailFragment.newInstance(noteData))
                .addToBackStack(null)
                .commit()
        } else {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, NoteDetailFragment.newInstance(noteData))
                .addToBackStack(null)
                .commit()
        }
    }
}