package com.kmp.newsapp.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kmp.newsapp.data.database.NewsDatabase
import com.kmp.newsapp.utils.AppPreferences
import com.kmp.newsapp.utils.dataStorePreferences
import com.kmp.newsapp.utils.getDatabaseBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val databaseModule = module {
    single {
        getRoomDatabase(getDatabaseBuilder())
    }

    single {
        AppPreferences(dataStorePreferences())
    }
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<NewsDatabase>
): NewsDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}