package com.kmp.newsapp.ui.newsDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.newsapp.data.model.Article
import com.kmp.newsapp.data.repository.OfflineNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val offlineNewsRepository: OfflineNewsRepository
) : ViewModel() {

    var isBookmarked by mutableStateOf(false)

    fun fetchArticleStatusFromDb(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            isBookmarked = offlineNewsRepository.getArticle(article.publishedAt) != null
        }
    }

    fun bookmarkArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isBookmarked) {
                offlineNewsRepository.upsertArticle(article)
            } else {
                offlineNewsRepository.deleteArticle(article)
            }
            isBookmarked = !isBookmarked
        }
    }
}