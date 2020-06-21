package com.github.falchio.notes.ui.note

import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.ui.base.BaseViewState

class NoteViewState(val note: Note? = null, error: Throwable?=null) : BaseViewState<Note?>(note, error)