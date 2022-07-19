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
import com.jordyf15.githubuser.databinding.FragmentFollowingBinding
import com.jordyf15.githubuser.utils.FragmentViewModelFactory

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var viewModelFactory: FragmentViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelFactory =
            FragmentViewModelFactory.getInstance(context.applicationContext as Application)
        val viewModel: FollowingViewModel by activityViewModels {
            viewModelFactory
        }
        followingViewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel.listFollowing.observe(viewLifecycleOwner) {
            showRecyclerList(it)
        }
        followingViewModel.errorMsg.observe(viewLifecycleOwner) {
            binding?.tvErrorMsg?.text = it
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followingViewModel.noDataMsg.observe(viewLifecycleOwner) {
            binding?.tvNoData?.text = it
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