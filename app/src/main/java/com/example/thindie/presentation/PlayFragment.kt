package com.example.thindie.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thindie.R
import com.example.thindie.databinding.PlayFragmentBinding
import com.example.thindie.domain.entities.GameResults
import com.example.thindie.domain.entities.GameSettings

class PlayFragment : Fragment() {
    private lateinit var viewModel: PlayViewModel
    private var _binding: PlayFragmentBinding? = null
    private val binding: PlayFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")
    private lateinit var gameSettings: GameSettings
    private lateinit var gameResults: GameResults

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
        _binding = PlayFragmentBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun gameFinish() {
        val finishFragment = GameFinishFragment.instance(gameResults)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.lay_main, finishFragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val NAME = "name"
        private const val GAME_SETTINGS = "settings"
        fun instance(gameSettings: GameSettings) = PlayFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GAME_SETTINGS, gameSettings)
            }
        }
    }
}