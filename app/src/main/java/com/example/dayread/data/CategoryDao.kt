package com.example.dayread.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dayread.network.BookInfo
import com.example.dayread.network.CategoryInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * from names ORDER BY list_name ASC")
    fun getAllCategories(): Flow<List<CategoryInfo>>

    @Query("SELECT * from books WHERE list_name_encoded = :category ORDER BY rank ASC")
    fun getAllBooks(category: String): Flow<List<BookInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: CategoryInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookInfo)
}