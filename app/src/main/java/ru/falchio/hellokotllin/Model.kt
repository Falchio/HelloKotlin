package ru.falchio.hellokotllin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Model {
    private val valueLiveData:MutableLiveData<String> = MutableLiveData()

    init {
        valueLiveData.value="Hello, world!"
    }

    fun value(): LiveData<String> = valueLiveData
}
