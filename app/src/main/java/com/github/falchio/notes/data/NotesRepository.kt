package com.github.falchio.notes.data

import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.provider.DataProvider


class NotesRepository(val remoteProvider: DataProvider) {
    fun getNotes() = remoteProvider.subscribeToAllNotes()
    suspend fun saveNote(note: Note) = remoteProvider.saveNote(note)
    suspend fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    suspend fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
}