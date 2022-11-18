package com.example.thindie.domain.useCase

import com.example.thindie.domain.entities.GameSettings
import com.example.thindie.domain.entities.Level

class GetGameSettingsUseCase(private val mathGameRepository: MathGameRepository) {
     operator fun invoke(level: Level) : GameSettings {
       return  mathGameRepository.getGameSettingsUseCase(level)
     }
}