package com.sbiktimirov.geekbrains.lessons.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sbiktimirov.geekbrains.lessons.notes.fragment.NoteListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, NoteListFragment())
            .commit()
    }
}