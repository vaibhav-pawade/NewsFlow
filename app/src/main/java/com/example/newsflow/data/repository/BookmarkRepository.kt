package com.example.newsflow.data.repository

import com.example.newsflow.data.local.BookmarkDao
import com.example.newsflow.data.local.BookmarkEntity
import com.example.newsflow.data.remote.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkRepository @Inject constructor(
    private val dao: BookmarkDao
) {
    fun getAll(): Flow<List<BookmarkEntity>> = dao.getAll()

    fun isBookmarked(url: String): Flow<Boolean> = dao.isBookmarked(url)


    suspend fun toggle(article: Article) {
        val url = article.url ?: return
    }

    suspend fun add(article: Article) {
        val url = article.url ?: return
        dao.upsert(
            BookmarkEntity(
                url = url,
                title = article.title,
                description = article.description,
                image = article.image
            )
        )
    }

    suspend fun remove(url: String) {
        dao.deleteByUrl(url)
    }

}