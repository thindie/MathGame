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
import androidx.lifecycle.ViewModelProvider
import com.example.thindie.R
import com.example.thindie.data.GameRepositoryImpl
import com.example.thindie.databinding.InGameFragmentBinding
import com.example.thindie.domain.entity.GameResult
import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var viewModel: GameFragmentViewModel
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
        level = requireArguments().getParcelable<Level>(LEVEL_KEY) as Level
        parseGameLevel(level)
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(requireContext(), "Игра приостановлена", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameFragmentViewModel::class.java]
        settingTimer()
        settingPlayingScreen()
        setAllOnClickListeners()
    }

    private fun settingListenersForGame(textView: TextView) {
        textView.setOnClickListener() {
            viewModel.onCLickParse(textView)
            settingPlayingScreen()
        }
    }

    private fun setAllOnClickListeners() {
        with(binding) {
            settingListenersForGame(OPTONE)
            settingListenersForGame(OPTTWO)
            settingListenersForGame(OPTTHREE)
            settingListenersForGame(OPTFOUR)
            settingListenersForGame(OPTFIVE)
            settingListenersForGame(OPTSIX)

        }

    }

    private fun settingPlayingScreen() {
        viewModel.settingGameViewDependOn(level)
        viewModel.settingButtonsView(binding)
        with(viewModel) {
            buttonOne.observe(viewLifecycleOwner) {
                binding.OPTONE.text = it.text
            }
            buttonTwo.observe(viewLifecycleOwner) {
                binding.OPTTWO.text = it.text
            }
            buttonThree.observe(viewLifecycleOwner) {
                binding.OPTTHREE.text = it.text
            }
            buttonFour.observe(viewLifecycleOwner) {
                binding.OPTFOUR.text = it.text
            }
            buttonFive.observe(viewLifecycleOwner) {
                binding.OPTFIVE.text = it.text
            }
            buttonSix.observe(viewLifecycleOwner) {
                binding.OPTSIX.text = it.text
            }

        }

    }

    private fun settingTimer() {
        val timer = gameSettings.gameTimeInSeconds * GAME_VIEW_MODEL_TIMER_MULTIPLIER
        object : CountDownTimer(timer, GAME_VIEW_MODEL_TIMER_MULTIPLIER) {
            @SuppressLint("SetTextI18n")
            override fun onTick(timer: Long) {
                binding.timeCount.text = "0:" + timer / GAME_VIEW_MODEL_TIMER_MULTIPLIER
            }

            override fun onFinish() {
                binding.timeCount.text = "0:0"
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

        gameSettings = when (level) {
            Level.TEST -> {
                gameRepository.getGameSettings(Level.TEST)
            }
            Level.EASY -> {
                gameRepository.getGameSettings(Level.EASY)
            }
            Level.NORMAL -> {
                gameRepository.getGameSettings(Level.NORMAL)
            }
            Level.HARD -> {
                gameRepository.getGameSettings(Level.HARD)
            }
        }
    }

    companion object {
        private const val GAME_VIEW_MODEL_TIMER_MULTIPLIER = 1000L
        const val NAME = "this_fragment_name"
        private const val LEVEL_KEY = "level"
        private val gameRepository = GameRepositoryImpl
        fun gameStarted(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_KEY, level)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}