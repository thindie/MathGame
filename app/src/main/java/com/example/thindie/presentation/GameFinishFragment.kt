package com.example.thindie.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        _binding = GamefinishFragmentBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "name"
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