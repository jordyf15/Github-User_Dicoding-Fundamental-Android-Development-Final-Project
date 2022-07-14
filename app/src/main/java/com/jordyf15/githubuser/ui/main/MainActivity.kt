package com.jordyf15.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordyf15.githubuser.R
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.databinding.ActivityMainBinding
import com.jordyf15.githubuser.ui.detail.DetailActivity
import com.jordyf15.githubuser.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mainViewModel by viewModels<MainViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        mainViewModel.listUsers.observe(this) {
            showRecyclerList(it)
        }

        mainViewModel.errorMsg.observe(this) {
            if(!it.isNullOrEmpty()) {
                binding.tvErrorMsg.text = it
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.noDataMsg.observe(this) {
            if(!it.isNullOrEmpty()){
                binding.tvNoData.text = it
            }
        }


        binding.svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUsers(query)
                binding.svUsers.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })

    }

    private fun showUserDetail(username: String) {
        val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.EXTRA_USERNAME, username)
        startActivity(detailIntent)
    }

    private fun showRecyclerList(list: List<User>) {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                showUserDetail(username)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}