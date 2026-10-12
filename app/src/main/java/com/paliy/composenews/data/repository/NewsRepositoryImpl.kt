package com.paliy.composenews.data.repository

import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.paliy.composenews.data.background.RefreshDataWorker
import com.paliy.composenews.data.local.ArticleDbModel
import com.paliy.composenews.data.local.NewsDao
import com.paliy.composenews.data.local.SubscriptionDbModel
import com.paliy.composenews.data.mapper.toDbModels
import com.paliy.composenews.data.mapper.toEntities
import com.paliy.composenews.data.remote.NewsApiService
import com.paliy.composenews.domain.entity.Article
import com.paliy.composenews.domain.entity.RefreshConfig
import com.paliy.composenews.domain.repository.NewsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApiService: NewsApiService,
    private val workManager: WorkManager
) : NewsRepository {
    override fun getAllSubscriptions(): Flow<List<String>> {
        return newsDao.getAllSubscriptions().map { subscriptions ->
            subscriptions.map { it.topic }
        }
    }

    override suspend fun addSubscription(topic: String) {
        newsDao.addSubscription(SubscriptionDbModel(topic))
    }

    override suspend fun updateArticlesForTopic(topic: String) {
        val articles = loadArticles(topic)
        newsDao.addArticles(articles)
    }

    private suspend fun loadArticles(topic: String): List<ArticleDbModel>{
        return try {
            newsApiService.loadArticles(topic).toDbModels(topic )
        } catch (e: Exception) {
            if (e is CancellationException){
                throw e
            }
            Log.e("NewsRepository", e.stackTraceToString())
            listOf()
        }
    }

    override suspend fun removeSubscription(topic: String) {
        newsDao.deleteSubscription(SubscriptionDbModel(topic))
    }

    override suspend fun updateArticlesForAllSubscriptions() {
        val subscriptions = newsDao.getAllSubscriptions().first()
        coroutineScope {
            subscriptions.forEach {
                launch {
                    updateArticlesForTopic(it.topic)
                }
            }
        }
    }

    override fun getArticlesByTopics(topic: List<String>): Flow<List<Article>> {
        return newsDao.getAllArticlesByTopics(topic).map {
            it.toEntities()
        }
    }

    private fun startBackgroundRefresh(refreshConfig: RefreshConfig){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(
                if (refreshConfig.wifiOnly){
                    NetworkType.UNMETERED
                }else{
                    NetworkType.CONNECTED
                }
            )
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            refreshConfig.interval.minutes.toLong(), TimeUnit.MINUTES
        ).setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = "Refresh data",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request = request
        )
    }

    override suspend fun clearAllArticles(topic: List<String>) {
        newsDao.deleteArticlesByTopics(topic)
    }
}