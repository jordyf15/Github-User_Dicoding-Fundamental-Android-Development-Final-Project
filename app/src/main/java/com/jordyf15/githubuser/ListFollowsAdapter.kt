package com.jordyf15.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordyf15.githubuser.databinding.ItemRowFollowBinding

class ListFollowsAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<ListFollowsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowFollowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, avatarUrl) = listUser[position]
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemUsername.text = login
    }

    override fun getItemCount() = listUser.size
}