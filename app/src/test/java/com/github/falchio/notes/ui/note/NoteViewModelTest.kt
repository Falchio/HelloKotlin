//package com.github.falchio.notes.ui.note
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.MutableLiveData
//import com.github.falchio.notes.data.NotesRepository
//import com.github.falchio.notes.data.entity.Note
//import com.github.falchio.notes.data.model.NoteResult
//import io.mockk.clearAllMocks
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class NoteViewModelTest{
//    @get:Rule
//    val taskExecutorRule = InstantTaskExecutorRule()
//
//    private val mockRepository = mockk<NotesRepository>(relaxed = true) // relaxed = true автогенерация методов с возвращаемыми значениями, иначе io.mockk.MockKException: no answer found for: NotesRepository(#1)
//    private val  noteLiveData = MutableLiveData<NoteResult>()
//
//    private val testNote = Note("1", "title", text = "text")
//    private lateinit var viewModel: NoteViewModel
//
//    @Before
//    fun setup(){
//        clearAllMocks()
//        every { mockRepository.getNoteById(testNote.id) } returns noteLiveData
//        every { mockRepository.deleteNote(testNote.id) } returns noteLiveData
//        viewModel = NoteViewModel(mockRepository)
//    }
//
//    @Test
//    fun `loadNote should return NoteData`(){
//        var result:NoteData.Data?=null
//        val testData = NoteData.Data(false, testNote)
//        viewModel.viewStateLiveData.observeForever {
//            result = it?.data
//        }
//
//        viewModel.loadNote(testNote.id)
//        noteLiveData.value= NoteResult.Success(testNote)
//        assertEquals(result,testData)
//    }
//
//    @Test
//    fun `loadNote should return error`(){
//        var result:Throwable?=null
//        val testData = Throwable("error")
//        viewModel.viewStateLiveData.observeForever {
//            result = it?.error
//        }
//
//        viewModel.loadNote(testNote.id)
//        noteLiveData.value= NoteResult.Error(error = testData)
//        assertEquals(result,testData)
//    }
//
//    @Test
//    fun `delete notes should return error`(){
//        var result:Throwable?=null
//        val testData = Throwable("error")
//        viewModel.viewStateLiveData.observeForever {
//            result = it?.error
//        }
//
//        viewModel.save(testNote)
//        viewModel.deleteNote()
//        noteLiveData.value= NoteResult.Error(error = testData)
//        assertEquals(result,testData)
//    }
//
//    @Test
//    fun `deleteNote should return NoteData with isDeleted`(){
//        var result:NoteData.Data?=null
//        val testData = NoteData.Data(true)
//        viewModel.viewStateLiveData.observeForever {
//            result = it?.data
//        }
//
//        viewModel.save(testNote)
//        viewModel.deleteNote()
//        noteLiveData.value= NoteResult.Success(null)
//        assertEquals(result,testData)
//    }
//
//    @Test
//    fun `should save changes`(){
//        viewModel.save(testNote)
//        viewModel.onCleared()
//        verify(exactly = 1) { mockRepository.saveNote(testNote) }
//    }
//}