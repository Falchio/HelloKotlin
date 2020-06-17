package com.github.falchio.notes.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.model.NoteResult
import com.github.falchio.notes.ui.base.BaseViewModel


class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {
    private var pendingNote: Note? = null

    fun save(note:Note){
        pendingNote = note
    }

    fun loadNote(noteId: String){
        NotesRepository.getNotesById(noteId).observeForever {
            it ?: return@observeForever
            when(it){
                is NoteResult.Success<*> ->{
                    viewStateLiveData.value = NoteViewState(note = it.data as? Note )
                }
                is NoteResult.Error ->{
                    viewStateLiveData.value = NoteViewState(error = it.error)
                }
            }

        }
    }

    // данный метод вызывается при нажатии кнопки назад, поэтому при уходе с экрана содержимое заметки будет сохраняться
    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}