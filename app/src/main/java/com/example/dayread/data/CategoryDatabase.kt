package com.example.dayread.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dayread.network.BookInfo
import com.example.dayread.network.CategoryInfo
import com.example.dayread.network.Converters
import com.example.dayread.ui.tools.Constants.DEFAULT_DB_NAME

@Database(
    version = 1,
    entities = [CategoryInfo::class, BookInfo::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var Instance: CategoryDatabase? = null

        fun getDatabase(context: Context): CategoryDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    CategoryDatabase::class.java,
                    DEFAULT_DB_NAME
                )
                    .build()
                    .also { Instance = it }
            }
    }
}