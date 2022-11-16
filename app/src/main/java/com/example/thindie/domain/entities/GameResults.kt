package com.example.thindie.domain.entities

data class GameResults(
    val solvedQuestions: Int,
    val totalQuestions: Int,
    val isWinner: Boolean
)
