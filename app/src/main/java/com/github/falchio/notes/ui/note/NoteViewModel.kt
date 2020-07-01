package com.github.falchio.notes.ui.note

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.model.NoteResult
import com.github.falchio.notes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel(val notesRepository: NotesRepository) : BaseViewModel<NoteData>() {

    private val pendingNote: Note?
        get() = getViewState().poll()?.note


    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun loadNote(noteId: String) = launch {

        try {
            notesRepository.getNoteById(noteId).let {
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let { notesRepository.deleteNote(it.id) }
            setData(NoteData(isDeleted = true))
        } catch (e: Throwable) {
            setError(e)
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let {
                notesRepository.saveNote(it)
            }
        }
    }
}
