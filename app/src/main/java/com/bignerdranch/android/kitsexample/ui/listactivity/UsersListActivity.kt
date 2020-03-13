package com.bignerdranch.android.kitsexample.ui.listactivity

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.kitsexample.R
import kotlinx.android.synthetic.main.activity_users_list.*


class UsersListActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(UserListViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        initSearchField()
        searchForResults("")
    }

    private fun updateUsersList() {
        val employeeAdapter = EmployeeAdapter()

        (search_results_recycler_view as RecyclerView).adapter = employeeAdapter
        viewModel.data.observe(this, Observer {
            employeeAdapter.submitList(it)
        })
    }

    private fun searchForResults(queryFilter: String) {
        viewModel.setQueryFilter(queryFilter)
        updateUsersList()
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(search_input_view.windowToken, 0)
    }

    private fun initSearchField() {
        search_start_view.setOnClickListener {
            hideKeyboard()
            searchForResults(search_input_view.text.toString())
        }
    }
}
