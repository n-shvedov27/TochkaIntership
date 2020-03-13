package com.bignerdranch.android.kitsexample.data.datasource

import androidx.paging.PageKeyedDataSource
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.network.GitHubRestApi


class FilteringEmployeePageKeyedDataSource(private val queryFilter: String) : PageKeyedDataSource<Int, UserItem>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
            val result = GitHubRestApi
                .filterGitHubUsers( queryFilter, 1, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result.items, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
            val result = GitHubRestApi
                .filterGitHubUsers(queryFilter, params.key, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result.items, params.key.inc())
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
            val result = GitHubRestApi
                .filterGitHubUsers(queryFilter, params.key, params.requestedLoadSize)
                .execute()
                .body()
            callback.onResult(result.items, params.key.dec())
    }
}