package com.jordyf15.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.jordyf15.githubuser.data.local.preference.SettingPreferences

class ThemeRepository private constructor(
    private val pref: SettingPreferences
) {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }


    companion object {
        @Volatile
        private var instance: ThemeRepository? = null
        fun getInstance(
            pref: SettingPreferences
        ): ThemeRepository =
            instance ?: synchronized(this) {
                instance ?: ThemeRepository(pref)
            }.also { instance = it }
    }
}