package com.bignerdranch.android.kitsexample.ui.profileactivity

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.kitsexample.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.util.*


class UserProfileActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
    }

    private var userInfo: MutableMap<String, MutableList<String>>? = null

    private fun loadImage(imageUrl: String?) {
        if (null != imageUrl) {
            Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .placeholder(R.drawable.empty_placeholder)
                .into(user_image)

        } else {
            user_image.setImageResource(R.drawable.empty_placeholder)
        }
    }

    private fun updateExpandableList() {
        val expandableListView = user_info as ExpandableListView
        val expandableListDetail = userInfo
        val expandableListTitle = ArrayList<String>(expandableListDetail!!.keys)

        val expandableListAdapter =
            CustomExpandableListAdapter(
                this,
                expandableListTitle,
                expandableListDetail.mapValues { it.value.toList() }.toMap()
            )
        expandableListView.setAdapter(expandableListAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val userName = intent.getStringExtra(USER_URL_EXTRA_KEY)

        userInfo = mutableMapOf(
            getString(R.string.repositories) to LinkedList(),
            getString(R.string.followers) to LinkedList()
        )

        observeUserInfo(userName)
    }

    private fun observeUserInfo(userName: String) {
        viewModel.getUser(userName).observe(this, Observer {
            loadImage(it.avatar_url)
            user_login.text = it.login
        })


        viewModel.getFollowers(userName).observe(this, Observer {
            userInfo!![getString(R.string.followers)] = it.map { it1 -> it1.login }.toMutableList()
            updateExpandableList()
        })

        viewModel.getRepositories(userName).observe(this, Observer {
            userInfo!![getString(R.string.repositories)] = it.map { it1 -> it1.name }.toMutableList()
            updateExpandableList()
        })
    }

    companion object {
        const val USER_URL_EXTRA_KEY = "user_name"
    }
}
