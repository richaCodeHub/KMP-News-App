package com.kmp.newsapp.data.repository

import com.kmp.newsapp.data.database.NewsDao
import com.kmp.newsapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn

class OfflineNewsRepository(
    private val newsDao: NewsDao
) {
    suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    fun getArticles() = newsDao.getArticles().flowOn(Dispatchers.IO)

    suspend fun getArticle(articleId: String): Article? {
        return newsDao.getArticle(articleId)
    }

    suspend fun deleteArticle(article: Article) {
        newsDao.deleteArticle(article)
    }

    suspend fun deleteAllArticles() {
        newsDao.deleteAllArticles()
    }
}