package com.example.newsflow.data.remote

data class NewsResponse( val articles: List<Article>)

data class Article(
    val title: String?,
    val description: String?,
    val image: String?,
    val url: String?
)
