package com.example.thindie.domain.useCase

import com.example.thindie.domain.entities.Level

class setQuestionUseCase(private val mathGameRepository: MathGameRepository) {
    operator fun invoke(level: Level){
        mathGameRepository.setQuestionUseCase(level)
    }
}