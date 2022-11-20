package com.example.thindie.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thindie.domain.entities.GameSettings

class PlayViewModelFactory(
    private val gameSettings: GameSettings,

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayViewModel::class.java)) {
            return PlayViewModel(
                gameSettings = gameSettings
            ) as T
        }else throw RuntimeException("malfunction in GVMF")
    }
}