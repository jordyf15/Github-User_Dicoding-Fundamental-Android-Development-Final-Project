package com.jordyf15.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.local.entity.Favorite
import com.jordyf15.githubuser.data.remote.response.DetailUser

class DetailViewModel(
    private val usersRepository: UsersRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    val userDetail: LiveData<DetailUser> = usersRepository.userDetail
    val errorMsg: LiveData<String> = usersRepository.detailViewErrorMsg
    val isLoading: LiveData<Boolean> = usersRepository.detailViewIsLoading

    fun getThemeSettings() = themeRepository.getThemeSettings()

    fun getUserDetail(username: String) = usersRepository.getUserDetail(username)

    fun insertFavorite(favorite: Favorite) = usersRepository.insertFavorite(favorite)

    fun deleteFavorite(username: String) = usersRepository.deleteFavorite(username)

    fun checkIsFavorite(username: String) = usersRepository.checkIsFavorite(username)
}