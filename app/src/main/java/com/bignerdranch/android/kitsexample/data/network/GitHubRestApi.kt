package com.bignerdranch.android.kitsexample.data.network

import com.bignerdranch.android.kitsexample.data.entities.RepositoryItem
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.entities.UserResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object GitHubRestApi {
    private val gitHubApi: GitHubSpecApi
    private const val TOKEN =
        "Basic bi1zaHZlZG92Mjc6NTA2MGUzNmIyOWIxMjcwN2ZjODhjZjEwMDNjMTAwZGI4MTdhYzQ0OA=="

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(" https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        gitHubApi = retrofit.create(GitHubSpecApi::class.java)
    }

    fun filterGitHubUsers(filter: String, page: Int, perPage: Int): Call<UserResponse> {
        return gitHubApi.filterGitHubUsers(filter, page, perPage, TOKEN)
    }

    fun getAllGitHubUsers(page: Int, perPage: Int): Call<List<UserItem>> {
        return gitHubApi.getAllGitHubUsers(page, perPage, TOKEN)
    }

    fun getUser(userName: String): Call<UserItem> {
        return gitHubApi.getUser(userName, TOKEN)
    }

    fun getFollowers(userName: String): Call<List<UserItem>> {
        return gitHubApi.getFollowers(userName, TOKEN)
    }

    fun getRepositories(userName: String): Call<List<RepositoryItem>> {
        return gitHubApi.getRepositories(userName, TOKEN)
    }

}