package com.example.thindie.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment

import com.example.thindie.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun start(){
        val fragment = WelcomeFragment.welcomeInstance()
        supportFragmentManager.beginTransaction()
            .add(fragment,null)
            .addToBackStack(WelcomeFragment.NAME)
            .commit()
        Log.d("Started","main")

    }
}