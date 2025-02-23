package com.kmp.newsapp.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val themeKey = stringPreferencesKey("theme")

    suspend fun getTheme() = dataStore.data.map {
        it[themeKey] ?: Theme.DARK_MODE.name
    }.first()

    suspend fun changeTheme(value: String) = dataStore.edit {
        it[themeKey] = value
    }
}