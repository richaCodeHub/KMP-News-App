package com.kmp.newsapp.di

import com.kmp.newsapp.ui.bookmark.BookmarkViewModel
import com.kmp.newsapp.ui.headline.HeadlineViewModel
import com.kmp.newsapp.ui.newsDetail.NewsDetailViewModel
import com.kmp.newsapp.ui.search.SearchViewModel
import com.kmp.newsapp.ui.settings.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HeadlineViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::BookmarkViewModel)
    viewModelOf(::NewsDetailViewModel)
    viewModelOf(::SettingViewModel)
}