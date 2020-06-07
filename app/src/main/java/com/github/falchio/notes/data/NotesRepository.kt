package com.github.falchio.notes.data

import com.github.falchio.notes.data.entity.Note


object NotesRepository {

    private val notes: List<Note> = listOf(
        Note(
            "Первая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            0xfff06292.toInt()
        ),
        Note(
            "Вторая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            0xff9575cd.toInt()
        ),
        Note(
            "Третья заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            0xff64b5f6.toInt()
        ),
        Note(
            "Четвертая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            0xff4db6ac.toInt()
        ),
        Note(
            "Пятая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            0xffb2ff59.toInt()
        ),
        Note(
            "Шестая заметка",
            "Текст первой заметки. Не очень длинный, но интересный",
            0xffffeb3b.toInt()
        )
    )

    fun getNotes(): List<Note> =notes

}