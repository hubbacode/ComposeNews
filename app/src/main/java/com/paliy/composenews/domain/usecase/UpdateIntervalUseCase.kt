package com.paliy.composenews.domain.usecase

import com.paliy.composenews.domain.entity.Interval
import com.paliy.composenews.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateIntervalUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(interval: Interval){
        settingsRepository.updateInterval(interval.minutes)
    }
}