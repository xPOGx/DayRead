package com.example.dayread.data

import com.example.dayread.network.Books
import com.example.dayread.network.Category
import com.example.dayread.network.CategoryApiService

interface CategoryNetworkRepository {
    suspend fun getCategories(): Category
    suspend fun getBooks(category: String): Books
}

class NetworkCategoryRepository(
    private val categoryApiService: CategoryApiService
) : CategoryNetworkRepository {
    override suspend fun getCategories() = categoryApiService.getCategories()
    override suspend fun getBooks(category: String) = categoryApiService.getBooks(category)
}