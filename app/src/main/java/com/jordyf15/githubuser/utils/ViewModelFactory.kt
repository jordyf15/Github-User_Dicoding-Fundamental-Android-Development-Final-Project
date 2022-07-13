package com.jordyf15.githubuser.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.di.Injection
import com.jordyf15.githubuser.ui.detail.DetailViewModel
import com.jordyf15.githubuser.ui.detail.FollowerViewModel
import com.jordyf15.githubuser.ui.detail.FollowingViewModel
import com.jordyf15.githubuser.ui.main.MainViewModel

class ViewModelFactory private constructor(private val usersRepository: UsersRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(usersRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(usersRepository) as T
        } else if (modelClass.isAssignableFrom(FollowerViewModel::class.java)) {
            return FollowerViewModel(usersRepository) as T
        } else if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}