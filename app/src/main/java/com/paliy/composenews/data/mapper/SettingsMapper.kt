package com.paliy.composenews.data.mapper

import com.paliy.composenews.domain.entity.RefreshConfig
import com.paliy.composenews.domain.entity.Settings

fun Settings.toRefreshConfig(): RefreshConfig{
    return RefreshConfig(language,interval,wifiOnly)
}