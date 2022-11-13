package com.example.thindie.presentation


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thindie.R
import com.example.thindie.data.GameRepositoryImpl
import com.example.thindie.databinding.InGameFragmentBinding
import com.example.thindie.domain.entity.GameResult
import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level

class GameFragment : Fragment() {
    private var _binding: InGameFragmentBinding? = null
    private val binding: InGameFragmentBinding
        get() = _binding ?: throw RuntimeException("binding == null")
    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private lateinit var gameResult: GameResult

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InGameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        level = requireArguments().getSerializable(LEVEL_KEY) as Level
        parseGameLevel(level)
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(requireContext(), "Игра приостановлена", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingTimer()
        //playingTheGame()
    }

    private fun playingTheGame() {
        val sum = gameSettings.theBiggestPossibleValue
        val minAnswers = gameSettings.minimumRightAnswersCountToWin
        val percentage = gameSettings.minimumPercentageOfRightQuestionsToWin

        var countAnswers = 0
        var successfulAnswers = 0

        while (true) {
            val question = GameRepositoryImpl
                .generateQuestion(sum, COUNT_OF_OPTIONS)

            val fragmentSum = question.sumInQuestion
            binding.sum.text = fragmentSum.toString()
            binding.visibleNumber.text = question
                .firstSumNumber.toString()

            val buttons = ArrayList<TextView>()

                with(binding) {
                    buttons.add(OPTONE)
                    buttons.add(OPTTWO)
                    buttons.add(OPTTHREE)
                    buttons.add(OPTFOUR)
                    buttons.add(OPTFIVE)
                    buttons.add(OPTSIX)
                }


            for (it in 0 until buttons.size) {
                with(buttons[it]) {
                    text = question.answerOptions[it].toString()
                    setOnClickListener() {
                        if (text.toString().toInt()
                            == question.secondSumNumberInvisible
                        ) {
                            successfulAnswers++
                            countAnswers++
                        }
                        else countAnswers++
                    }


                }
            }
        }
    }

    private fun settingTimer() {
        val timer = gameSettings.gameTimeInSeconds * GAME_SETTINGS_TIMER_MULTIPLIER
        object : CountDownTimer(timer, GAME_SETTINGS_TIMER_MULTIPLIER) {
            @SuppressLint("SetTextI18n")
            override fun onTick(timer: Long) {
                binding.timeCount.text = "0:" + timer / GAME_SETTINGS_TIMER_MULTIPLIER
            }

            override fun onFinish() {
                binding.timeCount.text = "0:00"

                launchGameFinishFragment()

            }

        }.start()
    }

    private fun launchGameFinishFragment() {
        gameResult = GameResult(true, 90, gameSettings, 20)
        val gameFinish = GameFinishedFragment.gameFinished(gameResult)
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack("")
            .replace(R.id.main_container, gameFinish)
            .commit()
    }

    private fun parseGameLevel(level: Level) {

        when (level) {
            Level.TEST -> {
                gameSettings = gameRepository.getGameSettings(Level.TEST)
            }
            Level.EASY -> {
                gameSettings = gameRepository.getGameSettings(Level.EASY)
            }
            Level.NORMAL -> {
                gameSettings = gameRepository.getGameSettings(Level.NORMAL)
            }
            Level.HARD -> {
                gameSettings = gameRepository.getGameSettings(Level.HARD)
            }
            else -> {
                throw RuntimeException("Level Unknown")
            }
        }
    }

    companion object {
        private const val GAME_SETTINGS_TIMER_MULTIPLIER = 1000L
        private const val COUNT_OF_OPTIONS = 6
        private const val LEVEL_KEY = "level"
        private val gameRepository = GameRepositoryImpl
        fun gameStarted(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(LEVEL_KEY, level)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}