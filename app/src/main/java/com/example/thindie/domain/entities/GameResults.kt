package com.example.thindie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResults(
    val solvedQuestions: Int,
    val totalQuestions: Int,
    val isWinner: Boolean,
    val gameSettings: GameSettings,
    val winRate: Int
) : Parcelable {


}
