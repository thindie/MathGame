package com.example.thindie.domain.entities

data class GameResults(
    val soldvedQuestions: Int,
    val totalQuestions: Int,
    val isWinner: Boolean
)
