package com.jordyf15.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.remote.response.User

class FollowingViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    val listFollowing: LiveData<List<User>> = usersRepository.listFollowing
    val isLoading: LiveData<Boolean> = usersRepository.followingViewIsLoading
    val errorMsg: LiveData<String> = usersRepository.followingViewErrorMsg
    val noDataMsg: LiveData<String> = usersRepository.followingViewNoData

    fun getFollowings(username: String) = usersRepository.getFollowings(username)
}