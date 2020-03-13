package com.bignerdranch.android.kitsexample.ui.listactivity

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.kitsexample.data.entities.UserItem

class EmployeeAdapter : PagedListAdapter<UserItem, EmployeeViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder =
        EmployeeViewHolder(parent)


    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) =
        holder.bindTo(getItem(position)!!)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
                oldItem == newItem
        }
    }
}