package com.github.falchio.notes.ui.splash

import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.errors.NoAuthException
import com.github.falchio.notes.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}
