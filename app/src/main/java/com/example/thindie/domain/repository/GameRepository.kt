package com.example.thindie.domain.repository

import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level
import com.example.thindie.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        theBiggestPossibleValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(
        level: Level
    ): GameSettings
}