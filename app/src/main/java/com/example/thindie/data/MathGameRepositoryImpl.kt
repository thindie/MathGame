package com.example.thindie.data

import com.example.thindie.domain.entities.GameSettings
import com.example.thindie.domain.entities.Level
import com.example.thindie.domain.entities.Question
import com.example.thindie.domain.useCase.MathGameRepository
import java.util.*

object MathGameRepositoryImpl : MathGameRepository {
    override fun getGameSettingsUseCase(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    5,
                    3,
                    50,
                    Level.TEST
                )
            }
            Level.EASY -> {
                GameSettings(
                    25,
                    10,
                    50,
                    Level.EASY
                )
            }
            Level.NORMAl -> {
                GameSettings(
                    35,
                    20,
                    70,
                    Level.NORMAl
                )
            }
            Level.HARD -> {
                GameSettings(
                    35,
                    25,
                    90,
                    Level.HARD
                )
            }
        }
    }

    override fun setQuestionUseCase(level: Level): Question {

        val range = when (level) {
            Level.TEST -> {
                TEST
            }
            Level.EASY -> {
                EASY
            }
            Level.NORMAl -> {
                NORMAL
            }
            Level.HARD -> {
                HARD
            }
        }
        val sum = Random().nextInt(range) + MIN_VALUE
        val visibleNumber = (sum + MIN_VALUE) - Random().nextInt(sum) + MIN_VALUE
        val solution = sum - visibleNumber
        val list = hashSetOf<Int>()
        list.add(solution)

        for (it in solution until solution + 3) {
            list.add(it)
        }
        for (it in solution until solution - 4) {
            list.add(it)
        }
        return Question(
            sum,
            visibleNumber,
            solution,
            list.toList()
        )
    }

    private const val MIN_VALUE = 2
    private const val TEST = 8
    private const val EASY = 10
    private const val NORMAL = 20
    private const val HARD = 30

}