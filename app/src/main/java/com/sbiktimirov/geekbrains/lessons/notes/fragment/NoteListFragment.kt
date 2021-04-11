package com.sbiktimirov.geekbrains.lessons.notes.fragment

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sbiktimirov.geekbrains.lessons.notes.data.NoteData
import com.sbiktimirov.geekbrains.lessons.notes.R
import com.sbiktimirov.geekbrains.lessons.notes.extension.DefaultListAdapter
import com.sbiktimirov.geekbrains.lessons.notes.extension.createRecycleViewListAdapter
import com.sbiktimirov.geekbrains.lessons.notes.viemodel.NoteListViewModel

class NoteListFragment : Fragment() {
    private val noteListViewModel: NoteListViewModel by activityViewModels()
    private lateinit var noteListRecycleView: RecyclerView
    private lateinit var adapter: DefaultListAdapter<NoteData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_note_list, container, false)

        initNoteListRecycleView(root)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_delete).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_add -> {
            noteListViewModel.showNote(noteListViewModel.addNote(), this)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initNoteListRecycleView(view: View) {
        adapter =
            createRecycleViewListAdapter<NoteData>(R.layout.note_list_item) { view, note ->
                view.findViewById<TextView>(R.id.note_title).text = note.title
                view.findViewById<TextView>(R.id.note_date).text = note.date.toString()
                view.setOnClickListener {
                    noteListViewModel.showNote(note, this)
                }

                view.setOnLongClickListener {
                    val popupMenu = PopupMenu(requireActivity(), it)
                    requireActivity().menuInflater.inflate(R.menu.main_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.action_add -> {
                                noteListViewModel.addNote()
                                true
                            }
                            R.id.action_delete -> {
                                noteListViewModel.deleteNote(note.id)
                                true
                            }
                            else -> true
                        }
                    }
                    popupMenu.show()
                    true
                }
            }

        noteListRecycleView = view.findViewById(R.id.note_list_recycle_view) as RecyclerView
        noteListRecycleView.layoutManager = LinearLayoutManager(context)
        noteListRecycleView.adapter = adapter

        noteListViewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}