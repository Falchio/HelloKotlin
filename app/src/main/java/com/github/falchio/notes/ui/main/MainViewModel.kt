package com.github.falchio.notes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.falchio.notes.data.NotesRepository


class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        NotesRepository.getNotes().observeForever {
            it?.let {
                viewStateLiveData.value =
                    viewStateLiveData.value?.copy(notes = it)   // если  viewStateLiveData.value?.copy(notes = it) == null
                    ?: MainViewState(it)                        // тогда берем notes из MainViewState(it)
            }
        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}
