package com.bignerdranch.android.kitsexample.ui.listactivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.kitsexample.R
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.ui.profileactivity.UserProfileActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.employee_item.view.*


class EmployeeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
) {
    private var item: UserItem? = null

    private fun loadImage(imageUrl: String?) {
        if (null != imageUrl) {
            itemView.item_cover_view.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(imageUrl)
                .fitCenter()
                .placeholder(R.drawable.empty_placeholder)
                .into(itemView.item_cover_view)

        } else {
            itemView.item_cover_view.setImageResource(R.drawable.empty_placeholder)
        }
    }

    fun bindTo(item: UserItem?) {
        this.item = item

        itemView.item_type_view.text = item?.login?.capitalize()
        loadImage(item?.avatar_url)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, UserProfileActivity::class.java)
            intent.putExtra(UserProfileActivity.USER_NAME_EXTRA_KEY, this.item?.login)
            itemView.context.startActivity(intent)
        }
    }
}