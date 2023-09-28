package com.example.dayread.data

import android.content.Context
import com.example.dayread.network.CategoryApiService
import com.example.dayread.ui.tools.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val categoryNetworkRepository: CategoryNetworkRepository
    val categoryOfflineRepository: CategoryOfflineRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }

    override val categoryNetworkRepository: CategoryNetworkRepository by lazy {
        NetworkCategoryRepository(retrofitService)
    }

    override val categoryOfflineRepository: CategoryOfflineRepository by lazy {
        OfflineCategoryRepository(CategoryDatabase.getDatabase(context).categoryDao())
    }
}