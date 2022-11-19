package com.example.thindie.domain.entity


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val theBiggestPossibleValue: Int,
    val minimumRightAnswersCountToWin: Int,
    val minimumPercentageOfRightQuestionsToWin: Int,
    val gameTimeInSeconds: Int
)  : Parcelable

