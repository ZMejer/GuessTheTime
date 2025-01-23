package com.example.guessthetime

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferences(private val context: Context) {

    private val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    private val USER_ID = intPreferencesKey("user_id")
    private val USER_POINTS = intPreferencesKey("user_points") // Dodanie klucza dla punktów

    suspend fun storeLogged(context: Context, isUserLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }

    fun getLoggedFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_USER_LOGGED_IN] ?: false
        }
    }

    suspend fun storeUserId(context: Context, userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    fun getUserIdFlow(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID] ?: 0
        }
    }

    suspend fun storeUserPoints(context: Context, points: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_POINTS] = points
        }
    }
    fun getUserPointsFlow(context: Context): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_POINTS] ?: 0
        }
    }
}
