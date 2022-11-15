package com.example.thindie.presentation


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.thindie.R
import com.example.thindie.databinding.FragmentGameFinishedBinding
import com.example.thindie.domain.entity.GameResult

class GameFinishedFragment : Fragment() {
    private val gameFinishedViewModel = GameFinishedViewModel()
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("Binding == null")
    private lateinit var gameResult: GameResult

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameResult = requireArguments()
            .getParcelable<GameResult>(GAME_RESULT) as GameResult

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        gameFinishedViewModel.settingGameResult(gameResult)
        fixingOnBackPress()
        settingOnClickListeners()
        settingFinishScreenView()


    }

    private fun settingFinishScreenView() {
        gameFinishedViewModel.settingFields(binding)
        gameFinishedViewModel.settingPicture(binding)
        with(gameFinishedViewModel) {
            neededPercent.observe(viewLifecycleOwner) {
                binding.tvRequiredPercentage.text = it.text
            }
            neededAnswersCount.observe(viewLifecycleOwner) {
                binding.tvRequiredAnswers.text = it.text
            }
            fieldOfScore.observe(viewLifecycleOwner) {
                binding.tvScoreAnswers.text = it.text
            }
            image.observe(viewLifecycleOwner) {
                if (image.value == true) {
                    binding.imageResult.setImageResource(R.drawable.happy)
                } else {
                    binding.imageResult.setImageResource(R.drawable.sad)
                }
            }
        }
    }

    private fun settingOnClickListeners() {
        with(binding) {
            buttonRetry.setOnClickListener {
                tryAgain()
            }
        }
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
            .popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        private const val GAME_RESULT = "result"
        fun gameFinished(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, gameResult)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}