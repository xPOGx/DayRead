package com.example.dayread.ui.screens.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayread.data.CategoryNetworkRepository
import com.example.dayread.data.CategoryOfflineRepository
import com.example.dayread.network.BookInfo
import com.example.dayread.network.BookResult
import com.example.dayread.network.Books
import com.example.dayread.network.Links
import com.example.dayread.ui.screens.home.NetworkStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val onlineRepository: CategoryNetworkRepository,
    private val offlineRepository: CategoryOfflineRepository
) : ViewModel() {
    private val category: String =
        checkNotNull(savedStateHandle[CategoryDestination.categoryEncodedArg])

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            updateUiState(_uiState.value.copy(status = NetworkStatus.Loading))
            if (!offlineLoad()) onlineLoad()
        }
    }

    private suspend fun onlineLoad() {
        try {
            val books = onlineRepository.getBooks(category)
            updateUiState(_uiState.value.copy(books = books, status = NetworkStatus.Done))
            writeToDatabase(books.results.encodedName, books.results.booksList)
        } catch (e: Exception) {
            updateUiState(_uiState.value.copy(status = NetworkStatus.Error))
        }
    }

    private suspend fun offlineLoad(): Boolean {
        val booksList = offlineRepository.getAllBooksStream(category).first()
        return if (booksList.isNotEmpty()) {
            updateUiState(
                _uiState.value.copy(
                    books = Books(
                        BookResult(
                            encodedName = category,
                            booksList = booksList
                        )
                    ),
                    status = NetworkStatus.Done
                )
            )
            true
        } else false
    }

    private suspend fun writeToDatabase(encodedName: String, bookList: List<BookInfo>) {
        bookList.forEach {
            val bookInfo = it.copy(encodedName = encodedName)
            offlineRepository.insertBook(bookInfo)
        }
    }

    fun updateUiState(uiState: CategoryUiState) = _uiState.update { uiState }
}

data class CategoryUiState(
    val books: Books = Books(BookResult("", listOf())),
    val status: NetworkStatus = NetworkStatus.Loading,
    val links: List<Links> = listOf()
)