package com.github.falchio.notes.ui.main

import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)