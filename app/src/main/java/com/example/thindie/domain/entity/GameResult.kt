package com.example.thindie.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult (
    val isWinner : Boolean,
    val countOfRightAnswers : Int,
    val gameSettings: GameSettings,
    val countOfQuestions:  Int
        ) : Parcelable
