package com.kmp.newsapp.di

import com.kmp.newsapp.data.database.NewsDatabase
import com.kmp.newsapp.data.repository.OfflineNewsRepository
import com.kmp.newsapp.data.repository.OnlineNewsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        OnlineNewsRepository(get())
    }
    single {
        OfflineNewsRepository(get<NewsDatabase>().newsDao())
    }
}