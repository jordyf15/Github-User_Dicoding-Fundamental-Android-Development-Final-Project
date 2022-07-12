package com.jordyf15.githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.jordyf15.githubuser.data.local.entity.UserEntity

class UserDiffCallback(private val mOldUserList: List<UserEntity>, private val mNewUserList: List<UserEntity>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldUserList.size

    override fun getNewListSize(): Int = mNewUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUserList[oldItemPosition]
        val newUser = mNewUserList[newItemPosition]
        return oldUser.username == newUser.username && oldUser.avatarUrl == newUser.avatarUrl
    }

}