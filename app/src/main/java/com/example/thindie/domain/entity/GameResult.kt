package com.example.thindie.domain.entity

data class GameResult (
    val isWinner : Boolean,
    val countOfRightAnswers : Int,
    val gameSettings: GameSettings,
    val countOfQuestions:  Int
        ) : java.io.Serializable
