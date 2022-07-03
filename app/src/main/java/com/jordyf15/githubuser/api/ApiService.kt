package com.jordyf15.githubuser.api

import com.jordyf15.githubuser.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun searchUsers(
        @Query("q") username: String
    ): Call<Search>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}
