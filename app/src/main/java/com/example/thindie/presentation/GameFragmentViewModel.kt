package com.example.thindie.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thindie.data.GameRepositoryImpl
import com.example.thindie.databinding.InGameFragmentBinding
import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level
import com.example.thindie.domain.entity.Question
import com.example.thindie.domain.useCases.GameSettingsDependOnLevelUseCase
import com.example.thindie.domain.useCases.GenerateQuestionsUseCase

class GameFragmentViewModel : ViewModel() {


    private val repository = GameRepositoryImpl
    private val gameSettingsUseCase = GameSettingsDependOnLevelUseCase(repository)
    private val generateQuestion = GenerateQuestionsUseCase(repository)
    private var _level: Level? = null

    private val _buttonOne = MutableLiveData<TextView>()
    private val _buttonTwo = MutableLiveData<TextView>()
    private val _buttonThree = MutableLiveData<TextView>()
    private val _buttonFour = MutableLiveData<TextView>()
    private val _buttonFive = MutableLiveData<TextView>()
    private val _buttonSix = MutableLiveData<TextView>()

    private val _progressBar = MutableLiveData<ContentLoadingProgressBar>()
    private val _textOfProgress = MutableLiveData<TextView>()

    private val _visibleNumber = MutableLiveData<TextView>()
    private val _sum = MutableLiveData<TextView>()

    private var gameSettings: GameSettings? = null
    private var _question: Question? = null
    private var countOfAnswers = COUNT_NOT_STARTED_ALREADY
    private var successfulAnswers = COUNT_NOT_STARTED_ALREADY


    val buttonOne: LiveData<TextView>
        get() = _buttonOne
    val buttonTwo: LiveData<TextView>
        get() = _buttonTwo
    val buttonThree: LiveData<TextView>
        get() = _buttonThree
    val buttonFour: LiveData<TextView>
        get() = _buttonFour
    val buttonFive: LiveData<TextView>
        get() = _buttonFive
    val buttonSix: LiveData<TextView>
        get() = _buttonSix


    val progressBar: LiveData<ContentLoadingProgressBar>
        get() = _progressBar
    val textOfProgress: LiveData<TextView>
        get() = _textOfProgress

    val visibleNumber: LiveData<TextView>
        get() = _visibleNumber
    val sum: LiveData<TextView>
        get() = _sum

    fun countOfRightAnswers() : Int{
        return successfulAnswers
    }
    fun countOfAllAnswers() : Int{
        return countOfAnswers
    }

    fun settingButtonsView(binding: InGameFragmentBinding) {

        with(binding) {
            _buttonOne.value = OPTONE.apply { text = _question!!.answerOptions[0].toString() }
            _buttonTwo.value = OPTTWO.apply { text = _question!!.answerOptions[1].toString() }
            _buttonThree.value = OPTTHREE.apply { text = _question!!.answerOptions[2].toString() }
            _buttonFour.value = OPTFOUR.apply { text = _question!!.answerOptions[3].toString() }
            _buttonFive.value = OPTFIVE.apply { text = _question!!.answerOptions[4].toString() }
            _buttonSix.value = OPTSIX.apply { text = _question!!.answerOptions[5].toString() }
            _visibleNumber.value =
                visibleNumber.apply { text = _question!!.firstSumNumber.toString() }
            _sum.value = sum.apply { text = _question!!.sumInQuestion.toString() }
        }
    }

    fun settingGameViewDependOn(level: Level) {
        _level = level
        if (gameSettings == null) {
            gameSettings = gameSettingsUseCase.invoke(_level!!)
        }
        _question = generateQuestion.invoke(gameSettings!!.theBiggestPossibleValue)

    }

    @SuppressLint("SetTextI18n")
    fun settingStringResultsView(binding: InGameFragmentBinding) {
        with(binding){
            _textOfProgress.value = answersProgress.apply {
                text = "Правильных ответов $successfulAnswers, (минимум ${gameSettings!!
                    .minimumRightAnswersCountToWin})"
            }
        }
    }

     fun settingProgressBar(binding: InGameFragmentBinding) {
        with(binding){
            _progressBar.value = contentLoadingProgressBar.apply {
                this.max = gameSettings!!.minimumRightAnswersCountToWin
                this.incrementProgressBy(successfulAnswers)

            }
        }

    }

    fun onCLickParse(textView: TextView) {
        val pretender = textView.text.toString().toInt()
        if (pretender == _question!!.secondSumNumberInvisible) {
            successfulAnswers++
            countOfAnswers++
            Log.d(
                "SUCCESS", "countOFanswers = ${countOfAnswers}" +
                        ", successful = ${successfulAnswers}"
            )

        } else {
            countOfAnswers++
            Log.d(
                "onFail", "countOFanswers = ${countOfAnswers}" +
                        ", successful = ${successfulAnswers}"
            )
        }
    }

    companion object {
        private const val COUNT_NOT_STARTED_ALREADY = 0
    }
}