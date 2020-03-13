package com.bignerdranch.android.kitsexample.ui.listactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bignerdranch.android.kitsexample.data.datasource.MySourceFactory
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import java.lang.AssertionError
import java.util.concurrent.Executors

class UserListViewModel : ViewModel() {
    private var queryFilter: String = ""
    private var allEmployee: LiveData<PagedList<UserItem>>? = null

    val data: LiveData<PagedList<UserItem>>
        get() {
            if (allEmployee == null) {
                val sourceFactory = MySourceFactory(queryFilter)

                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(PAGED_LIST_PAGE_SIZE)
                    .setInitialLoadSizeHint(PAGED_LIST_PAGE_SIZE)
                    .setEnablePlaceholders(PAGED_LIST_ENABLE_PLACEHOLDERS)
                    .build()

                allEmployee =
                    LivePagedListBuilder(sourceFactory, config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build()
            }
            return allEmployee ?: throw AssertionError()
        }

    fun setQueryFilter(queryFilter: String) {
        this.queryFilter = queryFilter
        allEmployee = null
    }

    companion object {
        private const val PAGED_LIST_PAGE_SIZE = 30
        private const val PAGED_LIST_ENABLE_PLACEHOLDERS = false
    }
}