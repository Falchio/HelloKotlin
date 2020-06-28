package com.github.falchio.notes.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.data.errors.NoAuthException
import com.github.falchio.notes.data.model.NoteResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FirestoreDataProviderTest {

    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val provider = FirestoreDataProvider(mockDb, mockAuth)
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDoc1 = mockk<DocumentSnapshot>()
    private val mockDoc2 = mockk<DocumentSnapshot>()
    private val mockDoc3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    @Before
    fun setup() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every {
            mockDb.collection(any()).document(any()).collection(any())
        } returns mockResultCollection
        every { mockDoc1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDoc2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDoc3.toObject(Note::class.java) } returns testNotes[2]
    }

    @Test
    fun `should throw NoAuthException if no auth`() {
        every { mockAuth.currentUser } returns null
        var result: Any? = null
        provider.subscribeToAllNotes().observeForever {
            result = (it as NoteResult.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        provider.saveNote(testNotes[0])
        verify(exactly = 1) { mockDocumentReference.set(testNotes[0]) }
    }

    @Test
    fun `subscribeToAllNotes returns notes`() {
        var result: List<Note>? = null                          // объявление необходимых для тестов переменных
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDoc1, mockDoc2, mockDoc3)           //подкладываются необходимые возвращаемые значения
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()       //захват листенера в переменную slot

        provider.subscribeToAllNotes().observeForever {                             //подписка на результат работы метода, листенер которого тестируется
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }

        slot.captured.onEvent(mockSnapshot, null)                                   // запускается листенер из тестируемого метода

        assertEquals(result, testNotes)                                             // сверяется результат работы листенера с ожидаемым результатом
    }

    @Test
    fun `saveNote returns Note`(){
        var result: Note?=null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>() // <in Void> - generic, don`t forget

        every{ mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.set(testNotes[0]).addOnSuccessListener (capture(slot)) } returns mockk()

        provider.saveNote(testNotes[0]).observeForever{
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(null)
        assertEquals(result, testNotes[0])
    }

    @Test
    fun `subscribeToAllNotes returns error`() {
        var result: Throwable? = null
        val mockException = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }

        slot.captured.onEvent(null, mockException)

        assertEquals(result, mockException)
    }
}