package com.paliy.composenews.domain.usecase

import com.paliy.composenews.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateWifiOnlyUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(wifiOnly: Boolean){
        settingsRepository.updateWifiOnly(wifiOnly)
    }
}