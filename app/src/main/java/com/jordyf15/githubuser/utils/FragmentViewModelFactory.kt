package com.jordyf15.githubuser.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.di.Injection
import com.jordyf15.githubuser.ui.detail.FollowerViewModel
import com.jordyf15.githubuser.ui.detail.FollowingViewModel

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
        fun getInstance(application: Application): FragmentViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FragmentViewModelFactory(
                    Injection.provideUserRepository(application)
                )
            }.also { instance = it }
    }
}