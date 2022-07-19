package com.jordyf15.githubuser.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.di.Injection
import com.jordyf15.githubuser.ui.detail.DetailViewModel
import com.jordyf15.githubuser.ui.favorite.FavoriteViewModel
import com.jordyf15.githubuser.ui.main.MainViewModel
import com.jordyf15.githubuser.ui.setting.SettingViewModel
import com.jordyf15.githubuser.ui.splash.SplashViewModel

class ActivityViewModelFactory private constructor(
    private val usersRepository: UsersRepository,
    private val themeRepository: ThemeRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(usersRepository, themeRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(usersRepository, themeRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(themeRepository) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(themeRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(usersRepository, themeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ActivityViewModelFactory? = null
        fun getInstance(
            application: Application,
            pref: SettingPreferences
        ): ActivityViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ActivityViewModelFactory(
                    Injection.provideUserRepository(application),
                    Injection.provideThemeRepository(pref)
                )
            }.also { instance = it }
    }
}