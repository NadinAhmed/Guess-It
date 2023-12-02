package com.nadin.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val SKIP_BUZZ_PATTERN = longArrayOf(100, 350, 100, 350)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

enum class BuzzType(val pattern: LongArray) {
    CORRECT(CORRECT_BUZZ_PATTERN),
    SKIP(SKIP_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN)
}

class GameViewModel : ViewModel() {

    companion object {
        // when the game is over
        private const val PANIC_TIME = 10_000L

        //number of ,milliseconds in one second
        private const val ONE_SECOND = 1000L

        //number of total time in the game
        private const val COUNTDOWN_TIME = 60_000L
    }

    // The current word
    private val _word = MutableLiveData("")
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinish = MutableLiveData(false)
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    //LiveData A
    private val _remainingTime = MutableLiveData<Long>()

    //LiveData B
    val remainingTimeString = _remainingTime.map { time ->
        DateUtils.formatElapsedTime(time / ONE_SECOND)
    }

    private val _buzz = MutableLiveData(BuzzType.NO_BUZZ)
    val buzz: LiveData<BuzzType>
        get() = _buzz

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private var timer: CountDownTimer

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        _buzz.value= BuzzType.SKIP
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        _buzz.value = BuzzType.CORRECT
        nextWord()
    }

    init {
        Log.i("GameViewModel", "GameViewModel created")
        resetList()
        nextWord()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = millisUntilFinished
                if (millisUntilFinished <= PANIC_TIME) {
                    _buzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _eventGameFinish.value = true
                _buzz.value = BuzzType.GAME_OVER
            }
        }

        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
        timer.cancel()
    }

    fun onGameFinishedComplete() {
        _eventGameFinish.value = false
    }

    fun onBuzzComplete() {
        _buzz.value = BuzzType.NO_BUZZ
    }
}
