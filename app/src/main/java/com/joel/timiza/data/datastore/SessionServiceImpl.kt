package com.joel.timiza.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_status")

class SessionServiceImpl @Inject constructor(
    context : Context
) : SessionService {

    private object PreferencesKey {
        val authStatusKey = booleanPreferencesKey(name = "auth_status_completed")
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnAuthStatus(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.authStatusKey] = completed
        }
    }

    override fun readAuthStatus(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val authState = preferences[PreferencesKey.authStatusKey] ?: false
                authState
            }

    }
}