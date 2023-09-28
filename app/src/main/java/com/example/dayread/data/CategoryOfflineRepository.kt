package com.example.dayread.data

import com.example.dayread.network.BookInfo
import com.example.dayread.network.CategoryInfo
import kotlinx.coroutines.flow.Flow

interface CategoryOfflineRepository {
    fun getAllCategoriesStream(): Flow<List<CategoryInfo>>
    fun getAllBooksStream(category: String): Flow<List<BookInfo>>
    suspend fun insertCategory(category: CategoryInfo)
    suspend fun insertBook(book: BookInfo)
}

class OfflineCategoryRepository(
    private val categoryDao: CategoryDao
) : CategoryOfflineRepository {
    override fun getAllCategoriesStream(): Flow<List<CategoryInfo>> = categoryDao.getAllCategories()
    override fun getAllBooksStream(category: String): Flow<List<BookInfo>> =
        categoryDao.getAllBooks(category)

    override suspend fun insertCategory(category: CategoryInfo) =
        categoryDao.insertCategory(category)

    override suspend fun insertBook(book: BookInfo) =
        categoryDao.insertBook(book)

}