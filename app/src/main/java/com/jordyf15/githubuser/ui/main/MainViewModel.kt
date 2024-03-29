package com.jordyf15.githubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.ThemeRepository
import com.jordyf15.githubuser.data.UsersRepository
import com.jordyf15.githubuser.data.remote.response.User

class MainViewModel(
    private val usersRepository: UsersRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    val listUsers: LiveData<List<User>> = usersRepository.listUsers
    val isLoading: LiveData<Boolean> = usersRepository.mainViewIsLoading
    val errorMsg: LiveData<String> = usersRepository.mainViewErrorMsg
    val noDataMsg: LiveData<String> = usersRepository.mainViewNoData

    init {
        searchUsers("john")
    }

    fun searchUsers(username: String) = usersRepository.searchUsers(username)
    fun getThemeSettings() = themeRepository.getThemeSettings()
}