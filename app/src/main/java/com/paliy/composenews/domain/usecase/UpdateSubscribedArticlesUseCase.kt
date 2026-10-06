package com.paliy.composenews.domain.usecase

import com.paliy.composenews.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateSubscribedArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){
    suspend operator fun invoke(){
        newsRepository.updateArticlesForAllSubscriptions()
    }
}