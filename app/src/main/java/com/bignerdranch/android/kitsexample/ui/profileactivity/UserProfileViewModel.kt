package com.bignerdranch.android.kitsexample.ui.profileactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.kitsexample.data.entities.RepositoryItem
import com.bignerdranch.android.kitsexample.data.entities.UserItem
import com.bignerdranch.android.kitsexample.data.repository.UserRepository

class UserProfileViewModel : ViewModel() {
    private var user: LiveData<UserItem>? = null
    private var repositories: LiveData<List<RepositoryItem>>? = null
    private var followers: LiveData<List<UserItem>>? = null

    fun getUser(userName: String): LiveData<UserItem> {
        if (user == null) {
            user = UserRepository().getProfiles(userName)
        }
        return user ?: throw AssertionError()
    }

    fun getRepositories(userName: String): LiveData<List<RepositoryItem>> {
        if (repositories == null) {
            repositories = UserRepository().getRepositories(userName)
        }
        return repositories ?: throw AssertionError()
    }

    fun getFollowers(userName: String): LiveData<List<UserItem>> {
        if (followers == null) {
            followers = UserRepository().getFollowers(userName)
        }
        return followers ?: throw AssertionError()
    }

}