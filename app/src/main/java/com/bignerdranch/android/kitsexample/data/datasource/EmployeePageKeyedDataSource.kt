package com.bignerdranch.android.kitsexample.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.network.GitHubRestApi
import java.io.IOException

class EmployeePageKeyedDataSource : PageKeyedDataSource<Int, UserItem>() {
    private fun parseIndexFromLinkHeader(string: String): Int =
        string.split("since=", "&")[1].toInt()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
        try {
            val result = GitHubRestApi
                .getAllGitHubUsers(1, params.requestedLoadSize)
                .execute()
            val nextIndex = parseIndexFromLinkHeader(result.headers().get("Link"))
            val body = result.body()
            callback.onResult(body, null, nextIndex)
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UserItem>
    ) {
        try {
            val result = GitHubRestApi
                .getAllGitHubUsers(params.key, params.requestedLoadSize)
                .execute()
            val nextIndex = parseIndexFromLinkHeader(result.headers().get("Link"))
            val body = result.body()

            callback.onResult(body, nextIndex)
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UserItem>
    ) {
        try {
            val result = GitHubRestApi
                .getAllGitHubUsers(params.key, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result, null)
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }
    }

    companion object {
        const val TAG = "EmployeeDataSource"
    }
}
