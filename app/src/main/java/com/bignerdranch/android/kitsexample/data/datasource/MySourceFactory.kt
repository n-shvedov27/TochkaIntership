package com.bignerdranch.android.kitsexample.data.datasource

import androidx.paging.DataSource
import com.bignerdranch.android.kitsexample.data.entities.UserItem


class MySourceFactory(private val queryFilter: String) : DataSource.Factory<Int, UserItem>() {
    override fun create(): DataSource<Int, UserItem> {
        return if (queryFilter != "") {
            FilteringEmployeePageKeyedDataSource(queryFilter)
        } else {
            EmployeePageKeyedDataSource()
        }
    }
}