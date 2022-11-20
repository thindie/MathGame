package com.example.thindie.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.thindie.R
import com.example.thindie.databinding.GamefinishFragmentBinding
import com.example.thindie.domain.entities.GameResults

class GameFinishFragment : Fragment() {
    private val _args by navArgs<GameFinishFragmentArgs>()
    private lateinit var gameResults: GameResults
    private var _binding: GamefinishFragmentBinding? = null
    private val binding: GamefinishFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")


    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameResults = _args.gameResults
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
        binding.buFinish.setOnClickListener { tryAgain() }
        if (gameResults.isWinner) {
            binding.imResult.setImageResource(R.drawable.happy)
        } else binding.imResult.setImageResource(R.drawable.sad)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun tryAgain() {
        findNavController().popBackStack()
    }


    companion object {


    }
}