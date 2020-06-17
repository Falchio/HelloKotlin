package com.github.falchio.notes.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.falchio.notes.R
import com.github.falchio.notes.data.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*


class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null) //Unit используется в Kotlin вместо void, в данном месте устанавливается click listener (функция с аргументом Note)
    : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(notes[pos])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.text

            val color = when (note.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
            }

            setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            itemView.setOnClickListener {
                onItemClick?.invoke(note) // здесь устанавливается лямбда из класса - class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null)
            }
        }
    }
}