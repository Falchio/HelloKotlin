package com.github.falchio.notes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.falchio.notes.R
import com.github.falchio.notes.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter({
            NoteActivity.start(
                this,
                it
            )  // здесь используется лямбда из класса - class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null)
        })
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer { state ->
            state?.let { state -> adapter.notes = state.notes }
        })

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

}
