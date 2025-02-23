package com.kmp.newsapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmp.newsapp.data.repository.OfflineNewsRepository
import com.kmp.newsapp.utils.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingViewModel(
    private val offlineNewsRepository: OfflineNewsRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {
    private val _currTheme: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentTheme = _currTheme.asStateFlow()

    init {
        getCurrTheme()
    }

    private fun getCurrTheme() = runBlocking {
        _currTheme.update {
            appPreferences.getTheme()
        }
    }

    fun changeTheme(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appPreferences.changeTheme(value)
            _currTheme.update { value }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            offlineNewsRepository.deleteAllArticles()
        }
    }
}