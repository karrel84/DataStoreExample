package me.rell.datastore2.preferences

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

enum class UiMode {
    LIGHT, DARK
}

class SettingManager(context: Context) {
    private val dataStore = context.createDataStore(name = "settings_pref")

    val uiModeFlow: Flow<UiMode> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val isDarkMode = preference[IS_DARK_MODE] ?: false

            if (isDarkMode) {
                return@map UiMode.DARK
            } else {
                return@map UiMode.LIGHT
            }
        }

    suspend fun setUiMode(uiMode: UiMode) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiMode) {
                UiMode.LIGHT -> false
                UiMode.DARK  -> true
            }
        }
    }

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"

        val IS_DARK_MODE = preferencesKey<Boolean>(KEY_DARK_MODE)
    }
}