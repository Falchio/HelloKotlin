package ru.falchio.hellokotllin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val model = Model()
    private val viewStateLiveData: MutableLiveData<String> = MutableLiveData()

    init{
        model.value().observeForever{string-> viewStateLiveData.value="Model says: $string"}
    }

    fun viewState(): LiveData<String> = viewStateLiveData
}
