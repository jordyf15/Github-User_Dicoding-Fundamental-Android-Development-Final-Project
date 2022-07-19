package com.jordyf15.githubuser.ui.detail

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordyf15.githubuser.data.remote.response.User
import com.jordyf15.githubuser.databinding.FragmentFollowerBinding
import com.jordyf15.githubuser.utils.FragmentViewModelFactory

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding
    private lateinit var viewModelFactory: FragmentViewModelFactory
    private lateinit var followerViewModel: FollowerViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelFactory =
            FragmentViewModelFactory.getInstance(context.applicationContext as Application)
        val viewModel: FollowerViewModel by activityViewModels {
            viewModelFactory
        }
        followerViewModel = viewModel
    }

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
            binding?.tvErrorMsg?.text = it
        }
        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followerViewModel.noDataMsg.observe(viewLifecycleOwner) {
            binding?.tvNoData?.text = it
        }
        val username = arguments?.getString(FOLLOWER_USERNAME) ?: ""
        if (username.isNotEmpty()) {
            followerViewModel.getFollowers(username)
        }
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