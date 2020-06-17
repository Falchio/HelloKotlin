package com.github.falchio.notes.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.falchio.notes.R
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.entity.Note.Color.*
import com.github.falchio.notes.ui.base.BaseActivity
import com.github.falchio.notes.ui.base.BaseViewModel
import com.github.falchio.notes.ui.base.BaseViewState
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*


class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.Note"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(
            context,
            NoteActivity::class.java
        ).run {       //после run следуют инструкции, что нужно делать с объектом, на котором run вызван
            noteId?.let {
                putExtra(EXTRA_NOTE, noteId)
            }
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_note

    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            safeNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?.let { viewModel.loadNote(it) }
            ?: let { supportActionBar?.title = getString(R.string.new_note_title) }
        initView()
    }

    override fun renderData(data: Note?) {
        this.note = data
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
