package com.github.falchio.notes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.falchio.notes.data.entity.Note
import java.util.*


object NotesRepository {
    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes=mutableListOf(
        Note(
            UUID.randomUUID().toString(),
            "Первая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Вторая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            Note.Color.YELLOW
        ),
        Note(
            UUID.randomUUID().toString(),
            "Третья заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            Note.Color.GREEN
        ),
        Note(
            UUID.randomUUID().toString(),
            "Четвертая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            Note.Color.BLUE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Пятая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            Note.Color.RED
        ),
        Note(
            UUID.randomUUID().toString(),
            "Шестая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            Note.Color.VIOLET
        )
    )

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> = notesLiveData

    fun saveNote(note:Note){
        addOrReplace(note)
        notesLiveData.value = notes
    }

    fun addOrReplace(note:Note){
        for (i in notes.indices){
            if (notes[i] == note){
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }

}