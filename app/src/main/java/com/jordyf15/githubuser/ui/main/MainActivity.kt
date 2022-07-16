package com.jordyf15.githubuser.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordyf15.githubuser.R
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.databinding.ActivityMainBinding
import com.jordyf15.githubuser.ui.detail.DetailActivity
import com.jordyf15.githubuser.ui.setting.SettingActivity
import com.jordyf15.githubuser.utils.ActivityViewModelFactory
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: ActivityViewModelFactory
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = SettingPreferences.getInstance(dataStore)
        viewModelFactory = ActivityViewModelFactory.getInstance(this, pref)
        mainViewModel = viewModelFactory.create(MainViewModel::class.java)

        binding.rvUsers.setHasFixedSize(true)

        mainViewModel.listUsers.observe(this) {
            showRecyclerList(it)
        }

        mainViewModel.errorMsg.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.tvErrorMsg.text = it
            }
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.noDataMsg.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.tvNoData.text = it
            }
        }

        mainViewModel.getThemeSettings().observe(this){
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true
        }
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