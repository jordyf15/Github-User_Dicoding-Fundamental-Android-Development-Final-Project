package com.jordyf15.githubuser.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.di.Injection
import com.jordyf15.githubuser.ui.detail.DetailViewModel
import com.jordyf15.githubuser.ui.detail.FollowerViewModel
import com.jordyf15.githubuser.ui.detail.FollowingViewModel
import com.jordyf15.githubuser.ui.main.MainViewModel
import com.jordyf15.githubuser.ui.setting.SettingViewModel

class FragmentViewModelFactory private constructor(
    private val usersRepository: UsersRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowerViewModel::class.java)) {
            return FollowerViewModel(usersRepository) as T
        } else if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FragmentViewModelFactory? = null
        fun getInstance(context: Context): FragmentViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FragmentViewModelFactory(
                    Injection.provideUserRepository(context)
                )
            }.also { instance = it }
    }
}