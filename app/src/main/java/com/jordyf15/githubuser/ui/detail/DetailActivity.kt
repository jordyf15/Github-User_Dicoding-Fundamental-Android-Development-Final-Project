package com.jordyf15.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jordyf15.githubuser.R
import com.jordyf15.githubuser.databinding.ActivityDetailBinding
import com.jordyf15.githubuser.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val detailViewModel by viewModels<DetailViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            if(!it.isNullOrEmpty()) {
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