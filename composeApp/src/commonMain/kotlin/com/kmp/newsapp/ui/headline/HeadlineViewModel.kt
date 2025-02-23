package com.kmp.newsapp.ui.headline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.newsapp.data.model.Article
import com.kmp.newsapp.data.model.ErrorResponse
import com.kmp.newsapp.data.model.NewsResponse
import com.kmp.newsapp.data.repository.OnlineNewsRepository
import com.kmp.newsapp.utils.Resource
import com.kmp.newsapp.utils.categoryList
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HeadlineViewModel(
    private val onlineNewsRepository: OnlineNewsRepository
) : ViewModel() {

    private val _newsStateFlow = MutableStateFlow<Resource<List<Article>>>(Resource.Loading)

    val newsStateFlow: StateFlow<Resource<List<Article>>>
        get() = _newsStateFlow

    var category by mutableStateOf(categoryList[0])

    init {
        getHeadlines(category)
    }

    fun getHeadlines(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _newsStateFlow.emit(Resource.Loading)
            try {
                val httpResponse = onlineNewsRepository.getNews(category)
                if (httpResponse.status.value in 200..299) {
                    val body = httpResponse.body<NewsResponse>()
                    _newsStateFlow.emit(Resource.Success(body.articles))
                } else {
                    val body = httpResponse.body<ErrorResponse>()
                    _newsStateFlow.emit(Resource.Error(body.message))
                }
            } catch (e: Exception) {
                _newsStateFlow.emit(Resource.Error(e.message.toString()))
            }
        }
    }
}