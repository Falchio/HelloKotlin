package com.github.falchio.notes.ui

import android.app.Application
import com.github.falchio.notes.data.di.appModule
import com.github.falchio.notes.data.di.mainModule
import com.github.falchio.notes.data.di.noteModule
import com.github.falchio.notes.data.di.splashModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }

}