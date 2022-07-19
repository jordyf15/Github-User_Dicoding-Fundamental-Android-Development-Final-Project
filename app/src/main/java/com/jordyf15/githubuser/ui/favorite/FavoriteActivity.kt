package com.jordyf15.githubuser.ui.favorite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordyf15.githubuser.R
import com.jordyf15.githubuser.data.local.entity.Favorite
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.databinding.ActivityFavoriteBinding
import com.jordyf15.githubuser.ui.setting.SettingActivity
import com.jordyf15.githubuser.utils.ActivityViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FavoriteActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var viewModelFactory: ActivityViewModelFactory
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val pref = SettingPreferences.getInstance(dataStore)
        viewModelFactory = ActivityViewModelFactory.getInstance(application, pref)
        favoriteViewModel = viewModelFactory.create(FavoriteViewModel::class.java)

        title = resources.getString(R.string.favorite_title)

        favoriteViewModel.getThemeSettings().observe(this) {
            setTheme(it)
        }

        favoriteViewModel.getAllFavorites().observe(this) {
            if (it.isEmpty()) {
                binding?.tvNoData?.text = "No favorite users found"
            } else {
                binding?.tvNoData?.text = ""
            }
            showRecyclerList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun showRecyclerList(list: List<Favorite>) {
        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        val listFavoriteAdapter = ListFavoriteAdapter(list)
        binding?.rvFavorites?.adapter = listFavoriteAdapter
    }

    private fun setTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}