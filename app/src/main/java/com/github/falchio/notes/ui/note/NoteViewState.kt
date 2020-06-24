package com.github.falchio.notes.ui.note

import com.github.falchio.notes.data.entity.Note
import com.github.falchio.notes.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}