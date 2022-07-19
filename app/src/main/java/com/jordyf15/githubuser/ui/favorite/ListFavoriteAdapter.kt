package com.jordyf15.githubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jordyf15.githubuser.data.local.entity.Favorite
import com.jordyf15.githubuser.databinding.ItemRowUserBinding
import com.jordyf15.githubuser.ui.detail.DetailActivity

class ListFavoriteAdapter(private val listFavorite: List<Favorite>) :
    RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val favorite = listFavorite[position]
        Glide.with(holder.itemView.context)
            .load(favorite.avatarUrl)
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemName.text = favorite.username
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, favorite.username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listFavorite.size
}


