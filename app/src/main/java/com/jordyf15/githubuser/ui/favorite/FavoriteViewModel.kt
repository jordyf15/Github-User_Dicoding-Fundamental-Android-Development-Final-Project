package com.jordyf15.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository

class FavoriteViewModel(
    private val usersRepository: UsersRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    fun getAllFavorites() = usersRepository.getAllFavorites()
    fun getThemeSettings() = themeRepository.getThemeSettings()

    init {
        getAllFavorites()
    }
}