package com.example.thindie.data

import android.util.Log
import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level
import com.example.thindie.domain.entity.Question
import com.example.thindie.domain.repository.GameRepository
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1


    override fun generateQuestion(
        theBiggestPossibleValue: Int,
        countOfOptions: Int
    ): Question {
        val sum = Random.nextInt(
            MIN_SUM_VALUE,
            theBiggestPossibleValue + 1
        )

        val visibleNumber = Random.nextInt(
            MIN_ANSWER_VALUE,
            sum - 1
        )

        val answerOptions = HashSet<Int>()
        val answer = (sum - visibleNumber)
        answerOptions.add(answer)
        val countFrom = answer - countOfOptions
        for (it in countFrom until sum) {
            if (it > 0 && answerOptions.size != countOfOptions) {
                answerOptions.add(it)
            }
        }
        Log.d("AnswerOptionsInQuestion", "is ${answerOptions.size}")
        return Question(sum, visibleNumber, answer, answerOptions.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    4
                )
            }
            Level.EASY -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.NORMAL -> {
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }
            Level.HARD -> {
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }

        }
    }
}


/* val fr= max(answer - countOfOptions, MIN_ANSWER_VALUE)
        val to=max(theBiggestPossibleValue, answer + countOfOptions)
        while (answerOptions.size < countOfOptions){
            answerOptions.add(Random.nextInt(fr,to))
      }*/
