package com.jordyf15.githubuser.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordyf15.githubuser.api.User
import com.jordyf15.githubuser.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, avatarUrl) = listUser[position]
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemName.text = login
        holder.itemView.setOnClickListener {
            if (login != null) {
                onItemClickCallback.onItemClicked(login)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }
}