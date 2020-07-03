package com.github.falchio.notes.ui.splash

import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.errors.NoAuthException
import com.github.falchio.notes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
            SplashViewState(authenticated = true)
        } ?: let {
            setError(NoAuthException())
        }
    }
}

