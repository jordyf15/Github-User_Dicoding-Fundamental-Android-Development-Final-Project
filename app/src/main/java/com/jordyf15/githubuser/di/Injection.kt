package com.jordyf15.githubuser.di

import android.content.Context
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.local.room.UsersDatabase
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UsersRepository {
        val apiService = ApiConfig.getApiService()
//        val database = UsersDatabase.getDatabase(context)
//        val dao = database.usersDao()
        return UsersRepository.getInstance(apiService)
    }
}