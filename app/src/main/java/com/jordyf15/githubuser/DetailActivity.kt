package com.jordyf15.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jordyf15.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

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
            Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show()
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
}