package com.paliy.composenews.data.mapper

import com.paliy.composenews.data.local.ArticleDbModel
import com.paliy.composenews.data.remote.NewsResponseDto
import com.paliy.composenews.domain.entity.Article
import java.text.SimpleDateFormat
import java.util.Locale

fun NewsResponseDto.toDbModels(topic: String): List<ArticleDbModel>{
    return articles.map {
        ArticleDbModel(
            title = it.title,
            description = it.description,
            imageUrl = it.urlToImage,
            sourceName = it.source.name,
            publishedAt = it.publishedAt.toTimestamp(),
            url = it.url,
            topic = topic
        )
    }
}

fun List<ArticleDbModel>.toEntities(): List<Article>{
    return map {
        Article(
            title = it.title,
            description = it.description,
            imageUrl = it.imageUrl,
            sourceName = it.sourceName,
            publishedAt = it.publishedAt,
            url = it.url
        )
    }.distinct()
}

private fun String.toTimestamp(): Long{
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return dateFormatter.parse(this)?.time ?: System.currentTimeMillis()
}

/*private fun String.toTimestamp(): Long {
    return Instant.parse(this).toEpochMilli()
}*/