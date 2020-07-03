package com.github.falchio.notes.data.provider

import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.entity.User
import com.github.falchio.notes.data.model.NoteResult
import kotlinx.coroutines.channels.ReceiveChannel

interface DataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}
