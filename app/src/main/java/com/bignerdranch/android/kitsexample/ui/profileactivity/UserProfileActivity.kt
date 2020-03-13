package com.bignerdranch.android.kitsexample.ui.profileactivity

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.kitsexample.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.util.*
import kotlin.collections.HashMap


class UserProfileActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
    }

    private fun hasInternetConnection() : Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private var userName: String? = null
    private var userImageUrl: String? = null
    private var userInfo: MutableMap<String, MutableList<String>>? = null

    private fun loadUserImage() {
        if (null != userImageUrl) {
            Glide.with(this)
                .load(userImageUrl)
                .fitCenter()
                .placeholder(R.drawable.empty_placeholder)
                .into(user_image)

        } else {
            user_image.setImageResource(R.drawable.empty_placeholder)
        }
    }

    private fun updateExpandableList() {
        val expandableListView = user_info as ExpandableListView
        val expandableListTitle = ArrayList<String>(userInfo!!.keys)

        val expandableListAdapter =
            CustomExpandableListAdapter(
                this,
                expandableListTitle,
                userInfo?.mapValues { it.value.toList() }!!.toMap()
            )
        expandableListView.setAdapter(expandableListAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        userName = intent.getStringExtra(USER_NAME_EXTRA_KEY)

        userInfo = mutableMapOf(
            getString(R.string.repositories) to LinkedList(),
            getString(R.string.followers) to LinkedList()
        )

        if (hasInternetConnection()) {
            userName?.let {
                observeUserInfo(it)
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet_message), Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        updateExpandableList()
        loadUserImage()
        user_login.text = userName ?: "undefined"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val a = userInfo as HashMap
        outState.putSerializable(USER_INFO_EXTRA_KEY, a)
        outState.putString(USER_NAME_EXTRA_KEY, userName)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        userInfo = savedInstanceState.getSerializable(USER_INFO_EXTRA_KEY) as HashMap<String, MutableList<String>>
        userName = savedInstanceState.getString(USER_NAME_EXTRA_KEY)
    }

    private fun observeUserInfo(userName: String) {
        viewModel.getUser(userName).observe(this, Observer {
            userImageUrl = it.avatar_url
            loadUserImage()
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
        const val USER_NAME_EXTRA_KEY = "user_name"
        const val USER_INFO_EXTRA_KEY = "user_info"
    }
}
