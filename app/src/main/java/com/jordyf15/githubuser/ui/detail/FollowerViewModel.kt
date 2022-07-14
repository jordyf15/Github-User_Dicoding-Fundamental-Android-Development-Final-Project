package com.jordyf15.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    val listFollower: LiveData<List<User>> = usersRepository.listFollower
    val isLoading: LiveData<Boolean> = usersRepository.followerViewIsLoading
    val errorMsg: LiveData<String> = usersRepository.followerViewErrorMsg
    val noDataMsg: LiveData<String> = usersRepository.followerViewNoData

    fun getFollowers(username: String) = usersRepository.getFollowers(username)
}