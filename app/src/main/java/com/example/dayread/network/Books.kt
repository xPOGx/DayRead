package com.example.dayread.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Books(
    val results: BookResult
)

@Serializable
data class BookResult(
    @SerialName(value = "list_name_encoded") val encodedName: String,
    @SerialName(value = "books") val booksList: List<BookInfo>
)

@Serializable
@Entity(tableName = "books")
data class BookInfo(
    @Transient
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Transient
    @ColumnInfo(name = "list_name_encoded")
    val encodedName: String = "",
    val rank: Int,
    val publisher: String,
    val description: String,
    val title: String,
    val author: String,
    val contributor: String,
    @SerialName(value = "book_image") val image: String,
    @SerialName(value = "buy_links") val links: List<Links>
)

@Serializable
data class Links(
    val name: String,
    val url: String
)

class Converters {
    @TypeConverter
    fun linksToString(value: List<Links>) =
        Json.encodeToString(value)

    @TypeConverter
    fun mapToLinks(value: String) =
        Json.decodeFromString<List<Links>>(value)
}