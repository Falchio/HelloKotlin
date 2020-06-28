package com.github.falchio.notes.data.di

import com.github.falchio.notes.data.NotesRepository
import com.github.falchio.notes.data.provider.DataProvider
import com.github.falchio.notes.data.provider.FirestoreDataProvider
import com.github.falchio.notes.ui.main.MainViewModel
import com.github.falchio.notes.ui.note.NoteViewModel
import com.github.falchio.notes.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirestoreDataProvider(get(), get()) } bind DataProvider::class
    single { NotesRepository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}