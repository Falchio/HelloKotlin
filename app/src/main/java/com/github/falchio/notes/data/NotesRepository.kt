package com.github.falchio.notes.data

import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.provider.FirestoreDataProvider
import com.github.falchio.notes.data.provider.RemoteDataProvider

object NotesRepository {
    val remoteProvider = FirestoreDataProvider()

    fun getNotes() = remoteProvider.subscribeAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNotesById (id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}