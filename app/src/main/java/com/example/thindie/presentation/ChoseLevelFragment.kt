package com.example.thindie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thindie.R
import com.example.thindie.data.MathGameRepositoryImpl
import com.example.thindie.databinding.ChoselevelFragmentBinding
import com.example.thindie.domain.entities.GameSettings
import com.example.thindie.domain.entities.Level
import com.example.thindie.domain.useCase.GetGameSettingsUseCase

class ChoseLevelFragment : Fragment() {
    private val mathGameRepository = MathGameRepositoryImpl
    private var _binding: ChoselevelFragmentBinding? = null
    private val binding: ChoselevelFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")
    private lateinit var gameSettings: GameSettings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChoselevelFragmentBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {

            buEasy.setOnClickListener {
                gameSettings = GetGameSettingsUseCase(mathGameRepository).invoke(Level.EASY)
                launchGame(PlayFragment.instance(gameSettings))
            }
            buNormal.setOnClickListener {
                gameSettings = GetGameSettingsUseCase(mathGameRepository)
                    .invoke(Level.NORMAl)
                launchGame(PlayFragment.instance(gameSettings))
            }
            buHard.setOnClickListener {
                gameSettings = GetGameSettingsUseCase(mathGameRepository)
                    .invoke(Level.HARD)
                launchGame(
                    PlayFragment.instance(gameSettings)
                )
            }
        }
    }


    private fun launchGame(playFragment: PlayFragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.lay_main, playFragment)
            .addToBackStack(ChoseLevelFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME = "ChoseLevelFragmnt"
        fun instance(): ChoseLevelFragment = ChoseLevelFragment()
    }


}
