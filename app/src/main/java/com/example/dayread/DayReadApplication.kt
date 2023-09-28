package com.example.dayread

import android.app.Application
import com.example.dayread.data.AppContainer
import com.example.dayread.data.DefaultAppContainer

class DayReadApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}