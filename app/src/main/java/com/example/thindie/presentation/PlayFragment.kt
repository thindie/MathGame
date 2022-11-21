package com.example.thindie.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thindie.databinding.PlayFragmentBinding
import com.example.thindie.domain.entities.GameResults
import com.example.thindie.domain.entities.GameSettings

class PlayFragment : Fragment() {

    private var _binding: PlayFragmentBinding? = null
    private val binding: PlayFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")

    private val _args by navArgs<PlayFragmentArgs>()
    private lateinit var gameSettings: GameSettings
    private val viewModel: PlayViewModel by lazy {
        ViewModelProvider(
            this, PlayViewModelFactory(
                gameSettings
            )
        )[PlayViewModel::class.java]
    }
    private lateinit var gameResults: GameResults
    override fun onAttach(context: Context) {
        gameSettings = _args.gameSettings
        super.onAttach(context)
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setGameFinishCause()
    }

    private fun setGameFinishCause() {
        viewModel.gameResultz.observe(viewLifecycleOwner) {
            gameResults = it
            findNavController().navigate(
                PlayFragmentDirections
                    .actionPlayFragmentToGameFinishFragment(gameResults)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}