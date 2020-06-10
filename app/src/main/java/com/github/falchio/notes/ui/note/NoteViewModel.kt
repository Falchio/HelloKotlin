package com.github.falchio.notes.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.entity.Note


class NoteViewModel : ViewModel() {
    private var pendingNote: Note? = null

    fun save(note:Note){
        pendingNote = note
    }

    // данный метод вызывается при нажатии кнопки назад, поэтому при уходе с экрана содержимое заметки будет сохраняться
    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}
