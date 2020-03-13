package com.bignerdranch.android.kitsexample.data.network

import com.bignerdranch.android.kitsexample.data.entities.RepositoryItem
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.entities.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubSpecApi {
    @GET("search/users")
    fun filterGitHubUsers(
        @Query("q") filter: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @GET("users")
    fun getAllGitHubUsers(
        @Query("since") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization") token: String
    ): Call<List<UserItem>>

    @GET("users/{user}")
    fun getUser(
        @Path("user") userName: String,
        @Header("Authorization") token: String
    ): Call<UserItem>

    @GET("users/{user}/followers")
    fun getFollowers(
        @Path("user") userName: String,
        @Header("Authorization") token: String
    ): Call<List<UserItem>>

    @GET("users/{user}/repos")
    fun getRepositories(
        @Path("user") userName: String,
        @Header("Authorization") token: String
    ): Call<List<RepositoryItem>>

}