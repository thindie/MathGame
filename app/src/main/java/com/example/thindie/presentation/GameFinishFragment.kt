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
import com.example.thindie.databinding.GamefinishFragmentBinding
import com.example.thindie.domain.entities.GameResults

class GameFinishFragment : Fragment() {
    private lateinit var gameResults: GameResults
    private var _binding: GamefinishFragmentBinding? = null
    private val binding: GamefinishFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")


    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameResults = requireArguments()
            .getParcelable<GameResults>(GAME_RESULT) as GameResults

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fixingOnBackPress()
        _binding = GamefinishFragmentBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buFinish.setOnClickListener { requireActivity().onBackPressed()

        }
        if(gameResults.isWinner){binding.imResult.setImageResource(R.drawable.happy)}
        else binding.imResult.setImageResource(R.drawable.sad)
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

    private fun tryAgain(){
        requireActivity().supportFragmentManager
            .popBackStack()
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.lay_main, ChoseLevelFragment.instance())
            .commit()

    }


    companion object {
        private const val NAME = "finish"
        private const val GAME_RESULT = "result"
        fun instance(gameResult: GameResults): GameFinishFragment {
            return GameFinishFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT, gameResult)
                }
            }
        }
    }
}