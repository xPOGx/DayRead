package com.example.dayread.network

import com.example.dayread.ui.tools.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApiService {
    @GET("lists/names")
    suspend fun getCategories(
        @Query(value = "api-key") apiKey: String = API_KEY
    ): Category

    @GET("lists/current/{category}")
    suspend fun getBooks(
        @Path(value = "category") category: String,
        @Query(value = "api-key") apiKey: String = API_KEY
    ): Books
}