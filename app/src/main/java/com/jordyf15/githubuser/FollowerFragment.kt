package com.jordyf15.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordyf15.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding
    private val followerViewModel: FollowerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followerViewModel.listFollower.observe(viewLifecycleOwner) {
            showRecyclerList(it)
        }
        followerViewModel.errorMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, "An error has occurred", Toast.LENGTH_SHORT).show()
        }
        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        val username = arguments?.getString(FOLLOWER_USERNAME) ?: ""
        followerViewModel.getFollowers(username)
    }

    private fun showRecyclerList(list: List<User>) {
        binding?.rvFollowers?.layoutManager = LinearLayoutManager(context)
        val listFollowsAdapter = ListFollowsAdapter(list)
        binding?.rvFollowers?.adapter = listFollowsAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val FOLLOWER_USERNAME = "follower_fragment_username"
    }
}