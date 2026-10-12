package com.paliy.composenews.domain.usecase

import com.paliy.composenews.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateNotificationsEnabledUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(enabled: Boolean){
        settingsRepository.updateNotificationsEnabled(enabled)
    }
}