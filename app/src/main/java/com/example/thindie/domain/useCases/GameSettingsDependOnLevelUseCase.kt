package com.example.thindie.domain.useCases


import com.example.thindie.domain.entity.GameSettings
import com.example.thindie.domain.entity.Level
import com.example.thindie.domain.repository.GameRepository

class GameSettingsDependOnLevelUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}