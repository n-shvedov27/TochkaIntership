package com.bignerdranch.android.kitsexample.data.datasource

import androidx.paging.PageKeyedDataSource
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.network.GitHubRestApi

class EmployeePageKeyedDataSource : PageKeyedDataSource<Int, UserItem>() {
    private fun parseIndexFromLinkHeader(string: String): Int =
        string.split("since=", "&")[1].toInt()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
        val result = GitHubRestApi
            .getAllHitHubUsers(1, params.requestedLoadSize)
            .execute()
        val nextIndex = parseIndexFromLinkHeader(result.headers().get("Link"))
        val body = result.body()
        callback.onResult(body, null, nextIndex)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        val result = GitHubRestApi
            .getAllHitHubUsers(params.key, params.requestedLoadSize)
            .execute()
        val nextIndex = parseIndexFromLinkHeader(result.headers().get("Link"))
        val body = result.body()

        callback.onResult(body, nextIndex)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        val result = GitHubRestApi
            .getAllHitHubUsers(params.key, params.requestedLoadSize)
            .execute()
            .body()
        callback.onResult(result, null)
    }
}
