package com.github.falchio.notes.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.model.NoteResult
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = FirebaseFirestore.getInstance()
    private val notesReference = store.collection(NOTES_COLLECTION)


    override fun subscribeAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.addSnapshotListener{querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                result.value = NoteResult.Error(it)
            } ?: let {
                querySnapshot?.let {snapshot ->
                    val notes = snapshot.documents.map {doc ->
                        doc.toObject(Note::class.java)
                    }
                    result.value = NoteResult.Success(notes)
                }
            }
        }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.document(id).get().addOnSuccessListener {
            result.value=NoteResult.Success(it.toObject(Note::class.java))
        }.addOnFailureListener{
            result.value = NoteResult.Error(it)
        }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.document(note.id).set(note).addOnSuccessListener {
            result.value=NoteResult.Success(note)
        }.addOnFailureListener{
            result.value = NoteResult.Error(it)
        }

        return result
    }
}