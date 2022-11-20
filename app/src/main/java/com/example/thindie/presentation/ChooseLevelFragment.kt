package com.example.thindie.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thindie.R
import com.example.thindie.databinding.ChooseLevelFragmentBinding
import com.example.thindie.domain.entity.Level

class ChooseLevelFragment : Fragment() {
    private var _binding: ChooseLevelFragmentBinding? = null
    private val binding: ChooseLevelFragmentBinding
        get() = _binding ?: throw RuntimeException("binding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChooseLevelFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun launchGameFragment(level: Level?) {
        val gameFragment = GameFragment.gameStarted(
            level
                ?: throw RuntimeException("Unknown level in ChooseLevelFragment fun launchGameFragment")
        )

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, gameFragment)
            .addToBackStack(GameFragment.NAME)
            .commit()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var clickOnLevel: Level?

        binding.buttonLevelEasy.setOnClickListener {
            clickOnLevel = Level.EASY
            launchGameFragment(clickOnLevel)
        }
        binding.buttonLevelMedium.setOnClickListener {
            clickOnLevel = Level.NORMAL
            launchGameFragment(clickOnLevel)
        }
        binding.buttonLevelHard.setOnClickListener {
            clickOnLevel = Level.HARD
            launchGameFragment(clickOnLevel)
        }
    }

    companion object {
       const val NAME = "restart"
        fun chooseLevelFragmentInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}