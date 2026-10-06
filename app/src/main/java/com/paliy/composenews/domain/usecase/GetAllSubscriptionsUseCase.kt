package com.paliy.composenews.domain.usecase

import com.paliy.composenews.domain.repository.NewsRepository
import javax.inject.Inject

class GetAllSubscriptionsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){
    operator fun invoke() = newsRepository.getAllSubscriptions()
}