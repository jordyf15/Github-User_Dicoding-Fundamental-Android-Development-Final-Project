package com.jordyf15.githubuser.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordyf15.githubuser.data.ThemeRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val themeRepository: ThemeRepository) : ViewModel() {
    fun getThemeSettings() = themeRepository.getThemeSettings()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            themeRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}