package com.jordyf15.githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.data.local.room.UsersDatabase
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig

object Injection {

    fun provideUserRepository(context: Context): UsersRepository {
        val apiService = ApiConfig.getApiService()
//        val database = UsersDatabase.getDatabase(context)
//        val dao = database.usersDao()
        return UsersRepository.getInstance(apiService)
    }

    fun provideThemeRepository(pref: SettingPreferences): ThemeRepository {
        return ThemeRepository.getInstance(pref)
    }
}