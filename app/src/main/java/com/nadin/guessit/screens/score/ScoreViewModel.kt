package com.nadin.guessit.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(score:Int):ViewModel() {

    private val _scoreText = MutableLiveData(score)
    val scoreText : LiveData<Int>
        get() = _scoreText

    private val _eventPlayAgain = MutableLiveData(false)
    val eventPlayAgain : LiveData<Boolean>
        get() = _eventPlayAgain

    fun onPlayAgainClicked(){
        _eventPlayAgain.value = true
    }
    fun onPlayAgainFinished(){
        _eventPlayAgain.value = false
    }
}