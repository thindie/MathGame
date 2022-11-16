package com.example.thindie.domain.useCase

import com.example.thindie.domain.entities.GameSettings
import com.example.thindie.domain.entities.Level
import com.example.thindie.domain.entities.Question

interface MathGameRepository {
   fun getGameSettingsUseCase(level: Level) : GameSettings
   fun setQuestionUseCase(level: Level) : Question
}