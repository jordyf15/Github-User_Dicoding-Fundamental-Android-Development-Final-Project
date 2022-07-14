package com.jordyf15.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.jordyf15.githubuser.data.local.room.UsersDao
import com.jordyf15.githubuser.data.remote.response.DetailUser
import com.jordyf15.githubuser.data.remote.response.Search
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.data.remote.retrofit.ApiConfig
import com.jordyf15.githubuser.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository private constructor(
    private val apiService: ApiService,
//    private val usersDao: UsersDao
) {
    val listUsers = MutableLiveData<List<User>>()
    val userDetail = MutableLiveData<DetailUser>()
    val listFollower = MutableLiveData<List<User>>()
    val listFollowing = MutableLiveData<List<User>>()
    val mainViewIsLoading = MutableLiveData<Boolean>()
    val detailViewIsLoading = MutableLiveData<Boolean>()
    val followerViewIsLoading = MutableLiveData<Boolean>()
    val followingViewIsLoading = MutableLiveData<Boolean>()
    val mainViewErrorMsg = MutableLiveData<String>()
    val detailViewErrorMsg = MutableLiveData<String>()
    val followerViewErrorMsg = MutableLiveData<String>()
    val followingViewErrorMsg = MutableLiveData<String>()
    val mainViewNoData = MutableLiveData<String>()
    val followerViewNoData = MutableLiveData<String>()
    val followingViewNoData = MutableLiveData<String>()
    var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun searchUsers(username: String) {
        mainViewIsLoading.value = true
        listUsers.value = emptyList()
        mainViewErrorMsg.value = ""

        val client = apiService.searchUsers(username)
        client.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                mainViewIsLoading.value = false
                if (response.isSuccessful) {
                    if (response.body()?.items?.isEmpty() == true) {
                        mainViewNoData.value = "No users found"
                    }
                    listUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.message().isEmpty()) {
                        mainViewErrorMsg.value = "An error has occurred"
                    } else {
                        mainViewErrorMsg.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                mainViewIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                if (t.message.toString().isEmpty()) {
                    mainViewErrorMsg.value = "An error has occurred"
                } else {
                    mainViewErrorMsg.value = t.message.toString()
                }
            }
        })
    }

    fun getUserDetail(username: String) {
        detailViewIsLoading.value = true
        userDetail.value = DetailUser()
        detailViewErrorMsg.value = ""

        val client = apiService.getDetailUser(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                detailViewIsLoading.value = false
                if (response.isSuccessful) {
                    userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.message().isEmpty()) {
                        detailViewErrorMsg.value = "An error has occurred"
                    } else {
                        detailViewErrorMsg.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                detailViewIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                if (t.message.toString().isEmpty()) {
                    detailViewErrorMsg.value = "An error has occurred"
                } else {
                    detailViewErrorMsg.value = t.message.toString()
                }
            }
        })
    }

    fun getFollowers(username: String) {
        followerViewIsLoading.value = true
        listFollower.value = emptyList()
        followerViewErrorMsg.value = ""

        val client = apiService.getUserFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                followerViewIsLoading.value = false
                if (response.isSuccessful) {
                    if (response.body()?.isEmpty() == true) {
                        followerViewNoData.value = "No followers found"
                    }
                    listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.message().isEmpty()) {
                        followerViewErrorMsg.value = "An error has occurred"
                    } else {
                        followerViewErrorMsg.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                followerViewIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                if (t.message.toString().isEmpty()) {
                    followerViewErrorMsg.value = "An error has occurred"
                } else {
                    followerViewErrorMsg.value = t.message.toString()
                }
            }

        })
    }

    fun getFollowings(username: String) {
        followingViewIsLoading.value = true
        listFollowing.value = emptyList()
        followingViewErrorMsg.value = ""

        val client = apiService.getUserFollowing(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                followingViewIsLoading.value = false
                if (response.isSuccessful) {
                    if (response.body()?.isEmpty() == true) {
                        followingViewNoData.value = "No followings found"
                    }
                    listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.message().isEmpty()) {
                        followingViewErrorMsg.value = "An error has occurred"
                    } else {
                        followingViewErrorMsg.value = response.message()
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                followingViewIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                if (t.message.toString().isEmpty()) {
                    followingViewErrorMsg.value = "An error has occurred"
                } else {
                    followingViewErrorMsg.value = t.message.toString()
                }
            }
        })
    }

    companion object {
        private val TAG = "UsersRepository"

        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            apiService: ApiService,
//            usersDao: UsersDao
        ): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository(apiService)
            }.also { instance = it }
    }
}