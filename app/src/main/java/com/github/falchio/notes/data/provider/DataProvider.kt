package com.github.falchio.notes.data.provider

import androidx.lifecycle.LiveData
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.entity.User
import com.github.falchio.notes.data.model.NoteResult

interface DataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun deleteNote(noteId: String): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>

}