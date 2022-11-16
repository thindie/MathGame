package com.example.thindie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val playingTime: Int,
    val solvedQuestions: Int,
    val percentage: Int,
    val level: Level
) : Parcelable
