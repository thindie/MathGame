package com.example.thindie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.thindie.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val welcomeFragment = WelcomeFragment.welcomeInstance()
        fragmentBehavior(welcomeFragment)



    }

    private fun fragmentBehavior(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .addToBackStack(fragment.tag)
            .attach(fragment)
            .commit()
    }
}