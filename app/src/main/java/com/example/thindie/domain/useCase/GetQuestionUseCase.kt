package com.example.thindie.domain.useCase

import com.example.thindie.domain.entities.Level
import com.example.thindie.domain.entities.Question

class GetQuestionUseCase(private val mathGameRepository: MathGameRepository) {
    operator fun invoke(level: Level) : Question{
      return  mathGameRepository.setQuestionUseCase(level)
    }
}