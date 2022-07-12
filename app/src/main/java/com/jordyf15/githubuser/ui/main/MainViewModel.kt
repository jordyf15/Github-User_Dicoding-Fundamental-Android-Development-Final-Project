package com.jordyf15.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.remote.response.Search
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    init {
        searchUsers("john")
    }

    fun searchUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(username)
        client.enqueue(object : Callback<Search> {
            override fun onResponse(
                call: Call<Search>,
                response: Response<Search>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _errorMsg.value = response.message()
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _errorMsg.value = t.message.toString()
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}