package com.jordyf15.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.remote.response.DetailUser
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val usersRepository: UsersRepository, private val themeRepository: ThemeRepository) : ViewModel() {
    val userDetail: LiveData<DetailUser> = usersRepository.userDetail
    val errorMsg: LiveData<String> = usersRepository.detailViewErrorMsg
    val isLoading: LiveData<Boolean> = usersRepository.detailViewIsLoading

    fun getThemeSettings() = themeRepository.getThemeSettings()

    fun getUserDetail(username: String) = usersRepository.getUserDetail(username)
}