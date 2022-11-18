package com.example.thindie.data

import android.util.Log
import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level
import com.example.thindie.domain.entity.Question
import com.example.thindie.domain.repository.GameRepository
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 3
    private const val MIN_ANSWER_VALUE = 1


    override fun generateQuestion(
        theBiggestPossibleValue: Int,
        countOfOptions: Int
    ): Question {
        val sum = Random.nextInt(
            MIN_SUM_VALUE,
            theBiggestPossibleValue
        )

        val visibleNumber = Random.nextInt(
            MIN_ANSWER_VALUE,
            sum
        )

        val answerOptions = HashSet<Int>()
        val answer = (sum - visibleNumber)
        answerOptions.add(answer)

        val countFrom = answer - countOfOptions
        for (it in countFrom until sum + 5) {
            if (it > 0 && answerOptions.size != countOfOptions) {
                answerOptions.add(it)
            }
        }

        while(answerOptions.size < 6){
            answerOptions.add(Random.nextInt(sum, theBiggestPossibleValue))
        }

        return Question(sum, visibleNumber, answer, answerOptions.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.EASY -> {
                GameSettings(
                    10,
                    8,
                    70,
                    25
                )
            }
            Level.NORMAL -> {
                GameSettings(
                    20,
                    20,
                    80,
                    35
                )
            }
            Level.HARD -> {
                GameSettings(
                    30,
                    30,
                    90,
                    35
                )
            }

        }
    }
}
