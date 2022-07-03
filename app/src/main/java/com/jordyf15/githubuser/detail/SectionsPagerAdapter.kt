package com.jordyf15.githubuser.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        when (position) {
            0 -> {
                fragment = FollowerFragment()
                bundle.putString(FollowerFragment.FOLLOWER_USERNAME, username)
            }
            1 -> {
                fragment = FollowingFragment()
                bundle.putString(FollowingFragment.FOLLOWING_USERNAME, username)
            }
        }
        fragment?.arguments = bundle
        return fragment as Fragment
    }

}