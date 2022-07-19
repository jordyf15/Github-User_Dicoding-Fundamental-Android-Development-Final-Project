package com.jordyf15.githubuser.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jordyf15.githubuser.R
import com.jordyf15.githubuser.data.local.entity.Favorite
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.data.remote.response.DetailUser
import com.jordyf15.githubuser.databinding.ActivityDetailBinding
import com.jordyf15.githubuser.ui.favorite.FavoriteActivity
import com.jordyf15.githubuser.ui.setting.SettingActivity
import com.jordyf15.githubuser.utils.ActivityViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModelFactory: ActivityViewModelFactory
    private lateinit var detailViewModel: DetailViewModel
    private var detailUser: DetailUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = resources.getString(R.string.detail_title)
        val pref = SettingPreferences.getInstance(dataStore)

        viewModelFactory = ActivityViewModelFactory.getInstance(application, pref)
        detailViewModel = viewModelFactory.create(DetailViewModel::class.java)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        detailViewModel.getUserDetail(username)
        detailViewModel.checkIsFavorite(username)

        binding.fabFavorite.setOnClickListener {
            favoriteUser()
        }

        binding.fabUnfavorite.setOnClickListener {
            unfavoriteUser()
        }

        detailViewModel.getThemeSettings().observe(this) {
            setTheme(it)
        }

        detailViewModel.checkIsFavorite(username).observe(this) {
            setFavoriteButton(it)
        }

        detailViewModel.userDetail.observe(this) {
            detailUser = it
            showUserDetail(it)
        }

        detailViewModel.errorMsg.observe(this) {
            showErrorMsg(it)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
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
                true
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.visibility = View.GONE
            binding.fabUnfavorite.visibility = View.VISIBLE
        } else {
            binding.fabUnfavorite.visibility = View.GONE
            binding.fabFavorite.visibility = View.VISIBLE
        }
    }

    private fun showUserDetail(detailUser: DetailUser) {
        binding.tvUsername.text = detailUser.login
        binding.tvRepository.text = getString(R.string.detail_repository, detailUser.publicRepos)
        Glide.with(this@DetailActivity)
            .load(detailUser.avatarUrl)
            .into(binding.imgAvatar)

        if (detailUser.name != null && detailUser.name.trim() != "") {
            binding.tvName.text = detailUser.name
            binding.tvName.visibility = View.VISIBLE
        } else binding.tvName.visibility = View.GONE

        if (detailUser.company != null && detailUser.company.trim() != "") {
            binding.tvCompany.text = detailUser.company
            binding.tvCompany.visibility = View.VISIBLE
        } else binding.tvCompany.visibility = View.GONE

        if (detailUser.location != null && detailUser.location.trim() != "") {
            binding.tvLocation.text = detailUser.location
            binding.tvLocation.visibility = View.VISIBLE
        } else binding.tvLocation.visibility = View.GONE

        val sectionsPagerAdapter = SectionsPagerAdapter(this, detailUser.login ?: "")
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
    }

    private fun showErrorMsg(errorMsg: String) {
        binding.tvErrorMsg.text = errorMsg
    }

    private fun favoriteUser() {
        if (detailUser != null) {
            val username = detailUser?.login ?: ""
            val avatarUrl = detailUser?.avatarUrl ?: ""
            val favorite = Favorite(username = username, avatarUrl = avatarUrl)
            detailViewModel.insertFavorite(favorite)
            detailViewModel.checkIsFavorite(username)
        }
    }

    private fun unfavoriteUser() {
        if (detailUser != null) {
            val username = detailUser?.login ?: ""
            detailViewModel.deleteFavorite(username)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}