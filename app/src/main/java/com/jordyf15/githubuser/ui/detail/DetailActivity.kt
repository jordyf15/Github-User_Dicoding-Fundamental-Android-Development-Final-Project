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
import com.jordyf15.githubuser.data.local.preference.SettingPreferences
import com.jordyf15.githubuser.databinding.ActivityDetailBinding
import com.jordyf15.githubuser.ui.setting.SettingActivity
import com.jordyf15.githubuser.utils.ActivityViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModelFactory: ActivityViewModelFactory
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detail User"
        val pref = SettingPreferences.getInstance(dataStore)

        viewModelFactory = ActivityViewModelFactory.getInstance(this, pref)
        detailViewModel = viewModelFactory.create(DetailViewModel::class.java)

        detailViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        detailViewModel.userDetail.observe(this) {
            binding.tvUsername.text = it.login
            binding.tvRepository.text = getString(R.string.detail_repository, it.publicRepos)
            Glide.with(this@DetailActivity)
                .load(it.avatarUrl)
                .into(binding.imgAvatar)

            if (it.name != null && it.name.trim() != "") {
                binding.tvName.text = it.name
                binding.tvName.visibility = View.VISIBLE
            } else binding.tvName.visibility = View.GONE

            if (it.company != null && it.company.trim() != "") {
                binding.tvCompany.text = it.company
                binding.tvCompany.visibility = View.VISIBLE
            } else binding.tvCompany.visibility = View.GONE

            if (it.location != null && it.location.trim() != "") {
                binding.tvLocation.text = it.location
                binding.tvLocation.visibility = View.VISIBLE
            } else binding.tvLocation.visibility = View.GONE

            val sectionsPagerAdapter = SectionsPagerAdapter(this, it.login ?: "")
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLE[position])
            }.attach()

        }
        detailViewModel.errorMsg.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.tvErrorMsg.text = it
            }
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            detailViewModel.getUserDetail(username)
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
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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