package com.bignerdranch.android.kitsexample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.kitsexample.data.entities.RepositoryItem
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.network.GitHubRestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    fun getProfiles(userName: String): LiveData<UserItem> {
        val profilesResponse = MutableLiveData<UserItem>()

        GitHubRestApi.getUser(userName)
            .enqueue(object : Callback<UserItem> {
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem> ) {
                    profilesResponse.setValue(response.body())
                }

                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    Log.e(TAG, t.message ?: "no message")
                }
            })

        return profilesResponse
    }

    fun getRepositories(userName: String): LiveData<List<RepositoryItem>> {
        val repositoriesResponse = MutableLiveData<List<RepositoryItem>>()

        GitHubRestApi.getRepositories(userName)
            .enqueue(object : Callback<List<RepositoryItem>> {
                override fun onResponse(call: Call<List<RepositoryItem>>, response: Response<List<RepositoryItem>> ) {
                    repositoriesResponse.setValue(response.body())
                }

                override fun onFailure(call: Call<List<RepositoryItem>>, t: Throwable) {
                    Log.e(TAG, t.message ?: "no message")
                }
            })

        return repositoriesResponse
    }

    fun getFollowers(userName: String): LiveData<List<UserItem>> {
        val repositoriesResponse = MutableLiveData<List<UserItem>>()

        GitHubRestApi.getFollowers(userName)
            .enqueue(object : Callback<List<UserItem>> {
                override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>> ) {
                    repositoriesResponse.setValue(response.body())
                }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    Log.e(TAG, t.message ?: "no message")
                }
            })

        return repositoriesResponse
    }

    companion object{
        const val TAG: String = "UserRepository"
    }
}