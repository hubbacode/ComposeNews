package com.paliy.composenews.domain.usecase

import com.paliy.composenews.domain.entity.Interval
import com.paliy.composenews.domain.entity.Language
import com.paliy.composenews.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: Language){
        settingsRepository.updateLanguage(language)
    }
}