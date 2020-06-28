package com.github.falchio.notes.ui.note

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.model.NoteResult
import com.github.falchio.notes.ui.base.BaseViewModel

class NoteViewModel(val notesRepository: NotesRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note


    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    fun loadNote(noteId: String) {
        notesRepository.getNoteById(noteId).observeForever { result ->
            result ?: return@observeForever
            when (result) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = result.data as? Note))
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesRepository.deleteNote(it.id).observeForever { result ->
                result ?: return@observeForever
                when (result) {
                    is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }

}
