package com.jordyf15.githubuser.data

import com.jordyf15.githubuser.data.local.room.UsersDao
import com.jordyf15.githubuser.data.remote.retrofit.ApiService

class UsersRepository private constructor(
    private val apiService: ApiService,
    private val usersDao: UsersDao
) {

    companion object{
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao
        ): UsersRepository =
            instance?: synchronized(this) {
                instance?: UsersRepository(apiService, usersDao)
            }.also { instance = it }
    }
}