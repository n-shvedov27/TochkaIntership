package com.bignerdranch.android.kitsexample.data.datasource

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.paging.PageKeyedDataSource
import com.bignerdranch.android.kitsexample.R
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.network.GitHubRestApi
import java.io.IOException


class FilteringEmployeePageKeyedDataSource(
    private val queryFilter: String
) : PageKeyedDataSource<Int, UserItem>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
        try {

            val result = GitHubRestApi
                .filterGitHubUsers(queryFilter, 1, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result.items, null, 2)
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        try {
            val result = GitHubRestApi
                .filterGitHubUsers(queryFilter, params.key, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result.items, params.key.inc())
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        try {
            val result = GitHubRestApi
                .filterGitHubUsers(queryFilter, params.key, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result.items, params.key.dec())
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }
    }

    companion object {
        const val TAG = "EmployeeDataSource"
    }
}