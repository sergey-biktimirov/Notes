package com.sbiktimirov.geekbrains.lessons.notes.fragment

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
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

        val adapter =
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

        noteListRecycleView = root.findViewById(R.id.note_list_recycle_view) as RecyclerView
        noteListRecycleView.layoutManager = LinearLayoutManager(context)
        noteListRecycleView.adapter = adapter

        noteListViewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return root
    }

    // TODO: 10.04.2021 Опциональное меню. 1. Добавить обработку действий меню.
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_add -> TODO()
        R.id.action_delete -> TODO()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}