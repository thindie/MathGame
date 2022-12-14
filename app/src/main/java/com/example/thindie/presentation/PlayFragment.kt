package com.example.thindie.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.thindie.R
import com.example.thindie.databinding.PlayFragmentBinding
import com.example.thindie.domain.entities.GameResults
import com.example.thindie.domain.entities.GameSettings

class PlayFragment : Fragment() {

    private var _binding: PlayFragmentBinding? = null
    private val binding: PlayFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")

    private val viewModel: PlayViewModel by lazy {
        ViewModelProvider(
            this, PlayViewModelFactory(gameSettings))[PlayViewModel::class.java]

    }
    private lateinit var gameSettings: GameSettings
    private lateinit var gameResults: GameResults
    private var listOfAnswerOptions: List<Int>? = COMES_WITH_VIEW_OBSERVE
    private var listTextViewOptions: List<TextView>? = COMES_WITH_VIEW_OBSERVE


    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameSettings = requireArguments()
            .getParcelable<GameSettings>(GAME_SETTINGS) as GameSettings
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fixingOnBackPress()
        _binding = PlayFragmentBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGameFinishCause()
        setTimer()
        setAnswerOptions()


    }


    private fun setProgressBar() {
        val progressBar = binding.pbProgress

        var totalAnswersNeed = 0
        var percentageGiven = 0
        progressBar.show()

        viewModel.totalAnswersNeed.observe(viewLifecycleOwner) {
            progressBar.progressBackgroundTintList.apply { getColorState(it, progressBar.progress) }
            progressBar.progress = it
            totalAnswersNeed = it
        }
        viewModel.rightAnswers.observe(viewLifecycleOwner) {
            progressBar.incrementProgressBy(it)
            val color = getColorState(it, totalAnswersNeed)
            progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.percentageGiven.observe(viewLifecycleOwner) {
            progressBar.secondaryProgress = it
            percentageGiven = it
        }
        viewModel.percentage.observe(viewLifecycleOwner) {
            progressBar.incrementProgressBy(it)
            val color = getColorState(it, percentageGiven)
            progressBar.progressBackgroundTintList = ColorStateList.valueOf(color)
        }


    }

    private fun getColorState(it: Int?, totalAnswersNeed: Int): Int {
        return if (it!! >= totalAnswersNeed) {
            Color.GREEN
        } else Color.RED
    }


    private fun setProgressString() {
        var rightAnswer = WILL_BE_SETTED_HERE
        var totalAnswer = WILL_BE_SETTED_HERE

        viewModel.stringInfo.observe(viewLifecycleOwner) {
            binding.tvProgress.text = it
        }
        viewModel.rightAnswers.observe(viewLifecycleOwner) {
            rightAnswer = it
        }
        viewModel.totalAnswersNeed.observe(viewLifecycleOwner) {
            totalAnswer = it
        }
        binding.tvProgress.setTextColor(getColorState(rightAnswer, totalAnswer))
    }

    private fun setTimer() {
        viewModel.time.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it.toString()
        }
    }

    private fun setAnswerOptions() {

        with(viewModel) {
            question.observe(viewLifecycleOwner) {
                listOfAnswerOptions = it.listOfVariants
                listTextViewOptions = listOf(
                    binding.tvOption1,
                    binding.tvOption2,
                    binding.tvOption3,
                    binding.tvOption4,
                    binding.tvOption5,
                    binding.tvOption6
                )
                with(binding) {
                    tvSum.text = it.sum.toString()
                    tvDigit.text = it.visibleNumber.toString()
                }
                for (iterate in 0 until listTextViewOptions!!.size) {
                    listTextViewOptions!![iterate].text = listOfAnswerOptions!![iterate].toString()
                    listTextViewOptions!![iterate].setOnClickListener {
                        val answer = listOfAnswerOptions!![iterate]
                        sendAnswer(answer)
                        setProgressBar()
                        setProgressString()

                    }
                }
            }
        }
    }


    private fun setGameFinishCause() {
        var pGiven = WILL_BE_SETTED_HERE
        var pCalc = WILL_BE_SETTED_HERE
        var rAnw = WILL_BE_SETTED_HERE
        var nAnw = WILL_BE_SETTED_HERE

        viewModel.percentageGiven.observe(viewLifecycleOwner) {
            pGiven = it
        }

        viewModel.percentage.observe(viewLifecycleOwner) {
            pCalc = it
        }

        viewModel.rightAnswers.observe(viewLifecycleOwner) {
            rAnw = it
        }

        viewModel.totalAnswersNeed.observe(viewLifecycleOwner) {
            nAnw = it
        }

        viewModel.finishGame.observe(viewLifecycleOwner) {

            val isWinner = (pCalc >= pGiven && rAnw >= nAnw)
            var totalQuestion = 0
            viewModel.totalQuestions.observe(viewLifecycleOwner) {
                totalQuestion = it
            }

            gameResults = GameResults(
                solvedQuestions = rAnw,
                totalQuestions = totalQuestion,
                isWinner
            )

            val finishFragment = GameFinishFragment.instance(gameResults)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.lay_main, finishFragment)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fixingOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    tryAgain()
                }
            }
        )
    }

    private fun tryAgain() {
        requireActivity().supportFragmentManager
            .popBackStack(ChoseLevelFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        const val NAME = "name"
        private val COMES_WITH_VIEW_OBSERVE = null
        private const val WILL_BE_SETTED_HERE = 0
        private const val GAME_SETTINGS = "settings"
        private const val NOT_SET_YET = 0
        fun instance(gameSettings: GameSettings) = PlayFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GAME_SETTINGS, gameSettings)
            }
        }
    }
}