package com.example.dayread.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayread.data.CategoryNetworkRepository
import com.example.dayread.data.CategoryOfflineRepository
import com.example.dayread.network.Category
import com.example.dayread.network.CategoryInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class NetworkStatus {
    Loading,
    Done,
    Error
}

class HomeViewModel(
    private val onlineRepository: CategoryNetworkRepository,
    private val offlineRepository: CategoryOfflineRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCategory()
    }

    fun loadCategory() {
        viewModelScope.launch {
            updateUiState(_uiState.value.copy(status = NetworkStatus.Loading))
            if (!offlineLoad()) onlineLoad()
        }
    }

    private suspend fun onlineLoad() {
        try {
            val category = onlineRepository.getCategories()
            val list = category.categoryList.sortedBy { it.displayName }
            val newCategory = category.copy(categoryList = list)
            updateUiState(_uiState.value.copy(category = newCategory, status = NetworkStatus.Done))
            writeToDatabase(list)
        } catch (e: Exception) {
            updateUiState(_uiState.value.copy(status = NetworkStatus.Error))
        }
    }

    private suspend fun offlineLoad(): Boolean {
        val categoryList = offlineRepository.getAllCategoriesStream().first()
        return if (categoryList.isNotEmpty()) {
            updateUiState(
                _uiState.value.copy(
                    category = Category(categoryList),
                    status = NetworkStatus.Done
                )
            )
            true
        } else false
    }

    private suspend fun writeToDatabase(categoryList: List<CategoryInfo>) =
        categoryList.forEach { offlineRepository.insertCategory(it) }

    private fun updateUiState(uiState: HomeUiState) {
        _uiState.update { uiState }
    }
}

data class HomeUiState(
    val category: Category = Category(listOf()),
    val status: NetworkStatus = NetworkStatus.Loading
)