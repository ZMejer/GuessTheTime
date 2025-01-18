package com.example.guessthetime

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferences(private val context: Context) {

    private val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")

    suspend fun storeLogged(context: Context,isUserLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }
    fun getLoggedFlow(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_USER_LOGGED_IN] ?: false
        }
    }

}
