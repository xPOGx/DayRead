package com.example.dayread.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dayread.DayReadApplication
import com.example.dayread.ui.screens.category.CategoryViewModel
import com.example.dayread.ui.screens.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                dayReadApplication().container.categoryNetworkRepository,
                dayReadApplication().container.categoryOfflineRepository
            )
        }
        initializer {
            CategoryViewModel(
                this.createSavedStateHandle(),
                dayReadApplication().container.categoryNetworkRepository,
                dayReadApplication().container.categoryOfflineRepository
            )
        }
    }
}

fun CreationExtras.dayReadApplication(): DayReadApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DayReadApplication)
