package com.example.thindie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thindie.R
import com.example.thindie.databinding.WelcomeFragmentBinding

class WelcomeFragment : Fragment() {

    private var _binding: WelcomeFragmentBinding? = null
    private val binding: WelcomeFragmentBinding
        get() = _binding ?: throw RuntimeException("Binding in ${this::class.java} == null")



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WelcomeFragmentBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()

    }

    private fun setButtonClickListener() {
        binding.buPlay.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_choseLevelFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
