package com.example.thindie.domain.entities

data class GameSettings(
    val playingTime: Int,
    val solvedQuestions: Int,
    val percentage: Int,
    val level: Level
)
