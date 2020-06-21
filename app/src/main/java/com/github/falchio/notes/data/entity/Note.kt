package com.github.falchio.notes.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(val id: String = "",val title: String = "", val text: String = "",
                val color: Color = Color.WHITE, val lastChanged: Date = Date())
    : Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true                 // если это тот же самый объект,
                                                        // "===" - ссылочное равенство, проверяет не ссылается ли входной класс на ту же область памяти, что и текущий
                                                        //тогда это тот же самый объект

        if (javaClass != other?.javaClass) return false // если other вообще объект другого класса

        other as Note                                   // после проверок можно быть уверенным, что входной объект other является точно объектом класса Note
                                                        // поэтому кастуем его в Note и начинаем обращаться как с Note
        if (id!=other.id) return false
        return true
    }

    enum class Color{
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
    }
}