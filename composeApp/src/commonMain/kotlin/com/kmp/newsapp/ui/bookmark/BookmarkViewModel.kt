package com.kmp.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.newsapp.data.model.Article
import com.kmp.newsapp.data.repository.OfflineNewsRepository
import com.kmp.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val offlineNewsRepository: OfflineNewsRepository
) : ViewModel() {

    private val _bookmarkStateFlow =
        MutableStateFlow<Resource<List<Article>>>(Resource.Loading)

    val bookmarkStateFlow: StateFlow<Resource<List<Article>>>
        get() = _bookmarkStateFlow

    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkStateFlow.emit(Resource.Loading)
            offlineNewsRepository.getArticles().catch {
                it.printStackTrace()
                _bookmarkStateFlow.emit(Resource.Error(it.message.toString()))
            }.collect { result ->
                _bookmarkStateFlow.emit(Resource.Success(result))
            }
        }
    }

}