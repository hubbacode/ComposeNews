package com.paliy.composenews.data.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponceDto(
    @SerialName("articles")
    val articles: List<ArticleDto> = listOf()
)