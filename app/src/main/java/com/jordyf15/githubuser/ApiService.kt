package com.jordyf15.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUsers(
        @Query("q") username: String
    ): Call<Search>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}
