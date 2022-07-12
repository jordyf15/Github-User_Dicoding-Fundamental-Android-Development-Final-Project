package com.jordyf15.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private val followingViewModel: FollowingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel.listFollowing.observe(viewLifecycleOwner) {
            showRecyclerList(it)
        }
        followingViewModel.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, "An error has occurred", Toast.LENGTH_SHORT).show()
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        val username = arguments?.getString(FOLLOWING_USERNAME) ?: ""
        followingViewModel.getFollowings(username)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerList(list: List<User>) {
        binding?.rvFollowings?.layoutManager = LinearLayoutManager(context)
        val listFollowsAdapter = ListFollowsAdapter(list)
        binding?.rvFollowings?.adapter = listFollowsAdapter
    }

    companion object {
        const val FOLLOWING_USERNAME = "following_fragment_username"
    }
}