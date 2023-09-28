package com.example.dayread.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Category(
    @SerialName(value = "results") val categoryList: List<CategoryInfo>
)

@Entity(tableName = "names")
@Serializable
data class CategoryInfo(
    @PrimaryKey(autoGenerate = true)
    @Transient
    val id: Int = 0,
    @ColumnInfo(name = "list_name")
    @SerialName(value = "list_name")
    val categoryName: String,
    @ColumnInfo(name = "display_name")
    @SerialName(value = "display_name")
    val displayName: String,
    @ColumnInfo(name = "list_name_encoded")
    @SerialName(value = "list_name_encoded")
    val encodedName: String,
    @ColumnInfo(name = "oldest_published_date")
    @SerialName(value = "oldest_published_date")
    val oldDate: String,
    @ColumnInfo(name = "newest_published_date")
    @SerialName(value = "newest_published_date")
    val newDate: String,
    val updated: String,
)
