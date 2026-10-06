package com.paliy.composenews.domain.repository

import com.paliy.composenews.domain.entity.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getAllSubscriptions(): Flow<List<String>>

    suspend fun addSubscription(topic: String)

    suspend fun updateArticlesForTopic(topic: String)

    suspend fun removeSubscription(topic: String)

    suspend fun updateArticlesForAllSubscriptions()

    fun getArticlesByTopics(topic: List<String>): Flow<List<Article>>

    suspend fun clearAllArticles(topic: List<String>)
}