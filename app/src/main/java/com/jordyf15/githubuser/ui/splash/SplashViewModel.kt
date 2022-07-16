package com.jordyf15.githubuser.ui.splash

import androidx.lifecycle.ViewModel
import com.jordyf15.githubuser.data.ThemeRepository

class SplashViewModel(private val themeRepository: ThemeRepository) : ViewModel() {
    fun getThemeSettings() = themeRepository.getThemeSettings()
}