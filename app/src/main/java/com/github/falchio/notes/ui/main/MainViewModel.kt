package com.github.falchio.notes.ui.main

import androidx.lifecycle.Observer
import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.model.NoteResult
import com.github.falchio.notes.ui.base.BaseViewModel


class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {
    private val notesObserver = Observer<NoteResult>{ result ->
        result?: return@Observer
        when (result){
            is NoteResult.Success<*> -> {
                viewStateLiveData.value= MainViewState(notes= result.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = result.error)
            }
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value=MainViewState()
        repositoryNotes.observeForever (notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }

}
