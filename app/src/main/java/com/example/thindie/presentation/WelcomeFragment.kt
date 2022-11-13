package com.example.thindie.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.thindie.R
import com.example.thindie.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private  var _binding: FragmentWelcomeBinding? = null
    private  val binding: FragmentWelcomeBinding
    get() = _binding ?: throw RuntimeException("binding = null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.OkayButton.setOnClickListener {
            launchChooseLevelFragment()
        }
    }

    private fun launchChooseLevelFragment(){
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(ChooseLevelFragment.NAME)
            .replace(R.id.main_container, ChooseLevelFragment.chooseLevelFragmentInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun welcomeInstance() : WelcomeFragment{
            return WelcomeFragment()
        }
    }
}