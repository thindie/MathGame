package com.example.thindie.presentation

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thindie.databinding.FragmentGameFinishedBinding
import com.example.thindie.domain.entity.GameResult

class GameFinishedViewModel : ViewModel() {
    private lateinit var gameResult: GameResult
    private val _image = MutableLiveData<Boolean>()
    private val _fieldOfScore = MutableLiveData<TextView>()
    private val _neededPercent = MutableLiveData<TextView>()
    private val _neededAnswersCount = MutableLiveData<TextView>()

    val fieldOfScore: LiveData<TextView>
        get() = _fieldOfScore
    val neededPercent: LiveData<TextView>
        get() = _neededPercent
    val image: LiveData<Boolean>
        get() = _image
    val neededAnswersCount: LiveData<TextView>
        get() = _neededAnswersCount


     fun settingPicture(binding: FragmentGameFinishedBinding) {
        with(binding) {
            if (gameResult.isWinner) {
                _image.value = true
            }
        }
    }

    fun settingFields(binding: FragmentGameFinishedBinding) {
        with(binding) {

            val countOfRightAnswers = gameResult.countOfRightAnswers
            val percentageDouble =
                ((countOfRightAnswers * 1.00) / (gameResult.countOfQuestions) * 1.00) * 100.00
            val percentage = percentageDouble.toInt()
            val requiredPercentage = gameResult.gameSettings
                .minimumPercentageOfRightQuestionsToWin
            val minToWin = gameResult.gameSettings
                .minimumRightAnswersCountToWin


            _fieldOfScore.value = tvScoreAnswers.apply {
                text = "Ваш процент правильных ответов $percentage%"

            }
            _neededPercent.value = tvRequiredPercentage.apply {
                text = "Необходимый процент правильных ответов $requiredPercentage"
            }

            _neededAnswersCount.value = tvRequiredAnswers.apply {
                text = "Необходимое количество правильных ответов $minToWin"
            }

            gameResult.isWinner = countOfRightAnswers >= minToWin &&
                    percentage > requiredPercentage

        }
    }

    fun settingGameResult(actualResult: GameResult) {
        gameResult = actualResult.copy()
    }
}