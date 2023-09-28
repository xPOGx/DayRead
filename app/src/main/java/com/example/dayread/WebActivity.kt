package com.example.dayread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dayread.ui.theme.DayReadTheme
import com.example.dayread.ui.tools.Constants.DEFAULT_URL
import com.example.dayread.ui.tools.Constants.STRING_KEY
import com.example.dayread.ui.web.WebScreen

class WebActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val link = intent.extras?.getString(STRING_KEY)
        setContent {
            DayReadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebScreen(
                        link = link ?: DEFAULT_URL
                    )
                }
            }
        }
    }
}