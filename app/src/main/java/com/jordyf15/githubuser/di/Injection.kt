package com.jordyf15.githubuser.di

import android.app.Application
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig

object Injection {

    fun provideUserRepository(application: Application): UsersRepository {
        val apiService = ApiConfig.getApiService()
        return UsersRepository.getInstance(apiService, application)
    }

    fun provideThemeRepository(pref: SettingPreferences): ThemeRepository {
        return ThemeRepository.getInstance(pref)
    }
}