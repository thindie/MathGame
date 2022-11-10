package com.example.thindie.domain.entity

data class GameSettings(
    val theBiggestPossibleValue: Int,
    val minimumRightAnswersCountToWin: Int,
    val minimumPercentageOfRightQuestionsToWin: Int,
    val gameTimeInSeconds: Int
)

