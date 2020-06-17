package com.github.falchio.notes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.falchio.notes.R
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.ui.base.BaseActivity
import com.github.falchio.notes.ui.base.BaseViewModel
import com.github.falchio.notes.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity <List<Note>?, MainViewState>() {
    override val viewModel: BaseViewModel<List<Note>?, MainViewState> by lazy{
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    lateinit var adapter: NotesRVAdapter
    override val layoutRes = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter {
            NoteActivity.start(this,it.id)  // здесь используется лямбда из класса - class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null)
        }
        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes=it
        }
    }

}
