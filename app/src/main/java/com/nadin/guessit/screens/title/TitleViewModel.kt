package com.nadin.guessit.screens.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel:ViewModel() {
    private val _eventPlayGame = MutableLiveData(false)
    val eventPlayGame : LiveData<Boolean>
        get() = _eventPlayGame

    fun onPlayGameClicked(){
        _eventPlayGame.value = true

    }
    fun onPlayGameFinished(){
        _eventPlayGame.value = false
    }

}