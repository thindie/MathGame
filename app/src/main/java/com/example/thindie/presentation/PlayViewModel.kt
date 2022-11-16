package com.example.thindie.presentation

import android.os.CountDownTimer
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thindie.data.MathGameRepositoryImpl
import com.example.thindie.domain.entities.GameSettings
import com.example.thindie.domain.entities.Level
import com.example.thindie.domain.entities.Question
import com.example.thindie.domain.useCase.GetQuestionUseCase

class PlayViewModel : ViewModel() {
    private lateinit var gameSetting: GameSettings
    private lateinit var level: Level
    private val repository = MathGameRepositoryImpl
    private var timer: CountDownTimer? = null

    private var playingTime: Int = COMES_WITH_GAMESETTINGS
    private var toSolveQuestions = COMES_WITH_GAMESETTINGS
    private var winPercentage: Int = COMES_WITH_GAMESETTINGS

    private var solution: Int = COMES_WITH_GENERATED_QUESTION
    private var visibleNumber = COMES_WITH_GENERATED_QUESTION
    private var sum: Int = COMES_WITH_GENERATED_QUESTION

    private var countOfAnswers: Int = TO_BE_ITERABLE
    private var answered: Int = TO_BE_ITERABLE


    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _progressbar = MutableLiveData<ProgressBar>()
    val progressbar: LiveData<ProgressBar>
        get() = _progressbar

    private val _stringBar = MutableLiveData<String>()
    val stringBar: LiveData<String>
        get() = _stringBar

    private val _summ = MutableLiveData<Int>()
    val summ: LiveData<Int>
        get() = _summ

    private val _visibleNum = MutableLiveData<Int>()
    val visibleNum: LiveData<Int>
        get() = _visibleNum


    fun startGame(gameSettings: GameSettings) {
        unPackGameSettings(gameSettings)
        setTimer()
        setQuestion()
        //finishGame()
    }

    fun sendAnswer(answer: Int) {
        if (answer == solution) {
            isItRightAnswer(true)
        } else isItRightAnswer(false)
    }


    //---------------------------private methods---------------------------------//
    private fun isItRightAnswer(boolean: Boolean) {
        if (boolean) {
            answered++
        }
        countOfAnswers++
        setQuestion()
    }

    private fun setQuestion() {
        _question.value = generateQuestion()
    }

    private fun generateQuestion(): Question {
        return GetQuestionUseCase(repository).invoke(level).apply {
             this@PlayViewModel.sum = this.sum
             this@PlayViewModel.visibleNumber = this.visibleNumber
        }
    }


    private fun finishGame() {

    }

    private fun setTimer() {
        timer = object : CountDownTimer(playingTime * TIMER_TICK, TIMER_TICK) {

            override fun onTick(millisUntilFinished: Long) {
                _time.value = formatTime(millisUntilFinished / TIMER_TICK)
            }

            override fun onFinish() {
                finishGame()
            }
        }.start()
    }

    private fun formatTime(l: Long): String {
        val seconds = 60
        val minutes = l / seconds
        val seconds_ = l - minutes
        return String.format("02%d:02d%", minutes, seconds_)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun unPackGameSettings(gameSettings: GameSettings) {
        gameSetting = gameSettings
        level = gameSetting.level
        playingTime = gameSetting.playingTime
        toSolveQuestions = gameSetting.solvedQuestions
        winPercentage = gameSetting.percentage
    }

    companion object {
        private const val TIMER_TICK = 1000L
        private const val COMES_WITH_GAMESETTINGS = 0
        private const val TO_BE_ITERABLE = 0
        private const val COMES_WITH_GENERATED_QUESTION = 0
    }

}