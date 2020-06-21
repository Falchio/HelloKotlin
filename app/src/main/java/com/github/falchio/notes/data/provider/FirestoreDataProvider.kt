package com.github.falchio.notes.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.entity.User
import com.github.falchio.notes.data.errors.NoAuthException
import com.github.falchio.notes.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"
    }

    private val store by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private val currentUser
        get() = auth.currentUser

    val userNotesCollection
        get() = currentUser?.let { store.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION) } ?: throw NoAuthException()

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply{
        value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
    }

    override fun subscribeAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply{
        userNotesCollection.addSnapshotListener{querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                value = NoteResult.Error(it)
            } ?: let {
                querySnapshot?.let {snapshot ->
                    val notes = snapshot.documents.map {doc ->
                        doc.toObject(Note::class.java)
                    }
                    value = NoteResult.Success(notes)
                }
            }
        }
    }

    override fun getNoteById(id: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        userNotesCollection.document(id).get().addOnSuccessListener {
            value=NoteResult.Success(it.toObject(Note::class.java))
        }.addOnFailureListener{
            value = NoteResult.Error(it)
        }
    }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply{
        userNotesCollection.document(note.id).set(note).addOnSuccessListener {
            value=NoteResult.Success(note)
        }.addOnFailureListener{
            value = NoteResult.Error(it)
        }
    }
}