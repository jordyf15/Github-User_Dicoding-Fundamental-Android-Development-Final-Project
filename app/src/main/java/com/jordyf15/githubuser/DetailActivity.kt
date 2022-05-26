package com.jordyf15.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jordyf15.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        binding.imgAvatar.setImageResource(user.avatar)
        binding.tvUsername.text = user.username
        binding.tvName.text = user.name
        binding.tvCompany.text = user.company
        binding.tvLocation.text = user.location
        binding.tvRepository.text = getString(R.string.detail_follower, user.repository)
        binding.tvFollower.text = getString(R.string.detail_follower, user.follower)
        binding.tvFollowing.text = getString(R.string.detail_following, user.following)
    }
}