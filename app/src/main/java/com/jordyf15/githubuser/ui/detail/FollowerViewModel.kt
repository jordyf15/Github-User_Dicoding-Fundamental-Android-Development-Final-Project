package com.jordyf15.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.remote.response.User

class FollowerViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    val listFollower: LiveData<List<User>> = usersRepository.listFollower
    val isLoading: LiveData<Boolean> = usersRepository.followerViewIsLoading
    val errorMsg: LiveData<String> = usersRepository.followerViewErrorMsg
    val noDataMsg: LiveData<String> = usersRepository.followerViewNoData

    fun getFollowers(username: String) = usersRepository.getFollowers(username)
}