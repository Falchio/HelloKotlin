package com.github.falchio.notes.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.falchio.notes.R
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.entity.Note.Color.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*


class NoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.Note"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, note: Note? = null) = Intent(
            context,
            NoteActivity::class.java
        ).run {       //после run следуют инструкции, что нужно делать с объектом, на котором run вызван
            note?.let {
                putExtra(EXTRA_NOTE, note)
            }
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            safeNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.new_note_title) //?: на случай если утверждение после let равно null

        initView()
    }

    private fun initView() {
        // чтобы не уйти в мертвый цикл снимает слушателей перед изменениями, в конце ставим вновь
        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        note?.let {
            et_title.setText(it.title)
            et_body.setText(it.text)

            val color = when (it.color) {
                WHITE -> R.color.white
                YELLOW -> R.color.yellow
                GREEN -> R.color.green
                BLUE -> R.color.blue
                RED -> R.color.red
                VIOLET -> R.color.violet
            }

            toolbar.setBackgroundColor(color)
        }

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    private fun safeNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

        note?.let {
            viewModel.save(it)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
